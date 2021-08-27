//,temp,JMSClientTest.java,536,567,temp,JMSClientTest.java,502,534
//,3
public class xxx {
    @Test(timeout=30000)
    public void testConsumerReceiveTimedThrowsWhenBrokerStops() throws Exception {
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
                        consumer.receive(100 + (i * 1000));
                        synchronized (consumer) {
                            consumer.notifyAll();
                        }
                    }
                    msg = "Should have thrown an IllegalStateException";
                } catch (Exception ex) {
                    LOG.info("Caught exception on receive(1000): {}", ex);
                }
            }
        };
        synchronized (consumer) {
            new Thread(t).start();
            consumer.wait(10000);
            consumer.notifyAll();
        }
        stopBroker();
        assertTrue(t.passed());
    }

};