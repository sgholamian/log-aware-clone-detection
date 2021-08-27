//,temp,AmqpSendReceiveTest.java,799,867,temp,AmqpSendReceiveTest.java,396,464
//,3
public class xxx {
    private void doTestReceiveMessageAndRefillCreditBeforeAcceptOnTopicAsync(Class<?> destType) throws Exception {
        final AmqpClient client = createAmqpClient();
        final LinkedList<Throwable> errors = new LinkedList<>();
        final CountDownLatch receiverReady = new CountDownLatch(1);
        ExecutorService executorService = Executors.newCachedThreadPool();

        final String address;
        if (Queue.class.equals(destType)) {
            address = "queue://" + getTestName();
        } else {
            address = "topic://" + getTestName();
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.info("Starting consumer connection");
                    AmqpConnection connection = trackConnection(client.connect());
                    AmqpSession session = connection.createSession();
                    AmqpReceiver receiver = session.createReceiver(address);
                    receiver.flow(1);
                    receiverReady.countDown();
                    AmqpMessage received = receiver.receive(5, TimeUnit.SECONDS);
                    assertNotNull(received);

                    receiver.flow(1);
                    received.accept();

                    received = receiver.receive(5, TimeUnit.SECONDS);
                    assertNotNull(received);
                    received.accept();

                    receiver.close();
                    connection.close();

                } catch (Exception error) {
                    errors.add(error);
                }
            }
        });

        // producer
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    receiverReady.await(20, TimeUnit.SECONDS);
                    AmqpConnection connection = trackConnection(client.connect());
                    AmqpSession session = connection.createSession();

                    AmqpSender sender = session.createSender(address);
                    for (int i = 0; i < 2; i++) {
                        AmqpMessage message = new AmqpMessage();
                        message.setMessageId("msg" + i);
                        sender.send(message);
                    }
                    sender.close();
                    connection.close();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);
        assertTrue("no errors: " + errors, errors.isEmpty());
    }

};