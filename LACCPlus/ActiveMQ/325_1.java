//,temp,AMQ3932Test.java,101,128,temp,AMQ3932Test.java,72,99
//,3
public class xxx {
    @Test
    public void testHungReceiveNoWait() throws Exception {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageConsumer consumer = session.createConsumer(session.createQueue(getClass().getName()));

        broker.stop();
        broker.waitUntilStopped();
        broker = null;

        final CountDownLatch done = new CountDownLatch(1);
        final CountDownLatch started = new CountDownLatch(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            public void run() {
                try {
                    started.countDown();
                    LOG.info("Entering into a Sync receiveNoWait call");
                    consumer.receiveNoWait();
                } catch (JMSException e) {
                }
                done.countDown();
            }
        });

        assertTrue(started.await(10, TimeUnit.SECONDS));
        assertTrue(done.await(20, TimeUnit.SECONDS));
    }

};