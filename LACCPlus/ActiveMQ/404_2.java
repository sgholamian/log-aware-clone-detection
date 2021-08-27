//,temp,PooledSessionExhaustionBlockTimeoutTest.java,130,179,temp,PooledSessionExhaustionTest.java,126,175
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testCanExhaustSessions() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionUri);
                    Connection connection = connectionFactory.createConnection();
                    connection.start();

                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    Destination destination = session.createQueue(QUEUE);
                    MessageConsumer consumer = session.createConsumer(destination);
                    for (int i = 0; i < NUM_MESSAGES; ++i) {
                        Message msg = consumer.receive(5000);
                        if (msg == null) {
                            return;
                        }
                        numReceived++;
                        if (numReceived % 20 == 0) {
                            LOG.debug("received " + numReceived + " messages ");
                            System.runFinalization();
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        ExecutorService threads = Executors.newFixedThreadPool(2);
        final CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {

            @Override
            public void run() {
                LOG.trace("Starting threads to send messages!");
            }
        });

        threads.execute(new TestRunner(barrier));
        threads.execute(new TestRunner(barrier));

        thread.join();

        // we should expect that one of the threads will die because it cannot acquire a session,
        // will throw an exception
        assertEquals(NUM_MESSAGES, numReceived);
        assertEquals(exceptionList.size(), 1);
    }

};