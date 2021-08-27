//,temp,JMSClientTest.java,536,567,temp,JMSClientTest.java,502,534
//,3
public class xxx {
    @Test(timeout=30000)
    public void testConsumerReceiveNoWaitThrowsWhenBrokerStops() throws Exception {
        connection = createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());
        connection.start();

        final MessageConsumer consumer=session.createConsumer(queue);
        Testable t = new Testable() {
            @Override
            public synchronized void run() {
                try {
                    for (int i = 0; i < 10; ++i) {
                        consumer.receiveNoWait();
                        synchronized (consumer) {
                            consumer.notifyAll();
                        }
                        TimeUnit.MILLISECONDS.sleep(1000 + (i * 100));
                    }
                    msg = "Should have thrown an IllegalStateException";
                } catch (Exception ex) {
                    LOG.info("Caught exception on receiveNoWait: {}", ex);
                }
            }

        };
        synchronized (consumer) {
            new Thread(t).start();
            consumer.wait(10000);
        }
        stopBroker();
        assertTrue(t.passed());
    }

};