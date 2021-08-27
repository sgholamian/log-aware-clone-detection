//,temp,AmqpSendReceiveTest.java,799,867,temp,AmqpSendReceiveTest.java,396,464
//,3
public class xxx {
    private void doTestSendReceiveLotsOfDurableMessages(Class<?> destType) throws Exception {
        final int MSG_COUNT = 1000;

        AmqpClient client = createAmqpClient();

        AmqpConnection connection = trackConnection(client.connect());
        AmqpSession session = connection.createSession();

        final CountDownLatch done = new CountDownLatch(MSG_COUNT);
        final AtomicBoolean error = new AtomicBoolean(false);
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final String address;
        if (Queue.class.equals(destType)) {
            address = "queue://" + getTestName();
        } else {
            address = "topic://" + getTestName();
        }

        final AmqpReceiver receiver = session.createReceiver(address);
        receiver.flow(MSG_COUNT);

        AmqpSender sender = session.createSender(address);

        final DestinationViewMBean destinationView;
        if (Queue.class.equals(destType)) {
            destinationView = getProxyToQueue(getTestName());
        } else {
            destinationView = getProxyToTopic(getTestName());
        }

        executor.execute(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < MSG_COUNT; i++) {
                    try {
                        AmqpMessage received = receiver.receive(5, TimeUnit.SECONDS);
                        received.accept();
                        done.countDown();
                    } catch (Exception ex) {
                        LOG.info("Caught error: {}", ex.getClass().getSimpleName());
                        error.set(true);
                    }
                }
            }
        });

        for (int i = 0; i < MSG_COUNT; i++) {
            AmqpMessage message = new AmqpMessage();
            message.setMessageId("msg" + i);
            sender.send(message);
        }

        assertTrue("did not read all messages, waiting on: " + done.getCount(), done.await(10, TimeUnit.SECONDS));
        assertFalse("should not be any errors on receive", error.get());

        assertTrue("Should be no inflight messages: " + destinationView.getInFlightCount(), Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return destinationView.getInFlightCount() == 0;
            }
        }));

        sender.close();
        receiver.close();
        connection.close();
    }

};