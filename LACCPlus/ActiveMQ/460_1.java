//,temp,JMSConsumerTest.java,563,642,temp,JMSConsumerTest.java,475,555
//,3
public class xxx {
    public void testMessageListenerAutoAckOnCloseWithPrefetch1() throws Exception {

        final AtomicInteger counter = new AtomicInteger(0);
        final CountDownLatch sendDone = new CountDownLatch(1);
        final CountDownLatch got2Done = new CountDownLatch(1);

        // Set prefetch to 1
        connection.getPrefetchPolicy().setAll(1);
        // This test case does not work if optimized message dispatch is used as
        // the main thread send block until the consumer receives the
        // message. This test depends on thread decoupling so that the main
        // thread can stop the consumer thread.
        connection.setOptimizedMessageDispatch(false);
        connection.start();

        // Use all the ack modes
        Session session = connection.createSession(false, ackMode);
        destination = createDestination(session, destinationType);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message m) {
                try {
                    TextMessage tm = (TextMessage)m;
                    LOG.info("Got in first listener: " + tm.getText());
                    assertEquals("" + counter.get(), tm.getText());
                    counter.incrementAndGet();
                    m.acknowledge();
                    if (counter.get() == 2) {
                        sendDone.await();
                        connection.close();
                        got2Done.countDown();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        // Send the messages
        sendMessages(session, destination, 4);
        sendDone.countDown();

        // Wait for first 2 messages to arrive.
        assertTrue(got2Done.await(100000, TimeUnit.MILLISECONDS));

        // Re-start connection.
        connection = (ActiveMQConnection)factory.createConnection();
        connections.add(connection);

        connection.getPrefetchPolicy().setAll(1);
        connection.start();

        // Pickup the remaining messages.
        final CountDownLatch done2 = new CountDownLatch(1);
        session = connection.createSession(false, ackMode);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message m) {
                try {
                    TextMessage tm = (TextMessage)m;
                    LOG.info("Got in second listener: " + tm.getText());
                    counter.incrementAndGet();
                    if (counter.get() == 4) {
                        done2.countDown();
                    }
                } catch (Throwable e) {
                    LOG.error("unexpected ex onMessage: ", e);
                }
            }
        });

        assertTrue(done2.await(1000, TimeUnit.MILLISECONDS));
        Thread.sleep(200);

        // close from onMessage with Auto_ack will ack
        // Make sure only 4 messages were delivered.
        assertEquals(4, counter.get());
    }

};