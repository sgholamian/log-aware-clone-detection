//,temp,JMSClientTest.java,445,478,temp,JMSClientTest.java,405,443
//,3
public class xxx {
    @Test(timeout=30000)
    public void testProducerCreateThrowsWhenBrokerStops() throws Exception {
        connection = createConnection();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue queue = session.createQueue(getDestinationName());
        connection.start();

        Testable t = new Testable() {
            @Override
            public synchronized void run() {
                try {
                    for (int i = 0; i < 10; ++i) {
                        MessageProducer producer = session.createProducer(queue);
                        synchronized (session) {
                            session.notifyAll();
                        }
                        if (producer == null) {
                            msg = "Producer should not be null";
                        }
                        TimeUnit.SECONDS.sleep(1);
                    }
                    msg = "Should have thrown an IllegalStateException";
                } catch (Exception ex) {
                    LOG.info("Caught exception on create producer: {}", ex);
                }
            }
        };
        synchronized (session) {
            new Thread(t).start();
            session.wait(10000);
        }
        stopBroker();
        assertTrue(t.passed());
    }

};