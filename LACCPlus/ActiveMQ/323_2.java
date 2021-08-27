//,temp,JMSClientTest.java,445,478,temp,JMSClientTest.java,405,443
//,3
public class xxx {
    @Test(timeout=30000)
    public void testProducerThrowsWhenBrokerStops() throws Exception {

        connection = createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(getDestinationName());
        connection.start();

        final MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        final Message m = session.createTextMessage("Sample text");

        Testable t = new Testable() {
            @Override
            public synchronized void run() {
                try {
                    for (int i = 0; i < 30; ++i) {
                        producer.send(m);
                        synchronized (producer) {
                            producer.notifyAll();
                        }
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    msg = "Should have thrown an IllegalStateException";
                } catch (Exception ex) {
                    LOG.info("Caught exception on send: {}", ex);
                }
            }
        };
        synchronized(producer) {
            new Thread(t).start();
            // wait until we know that the producer was able to send a message
            producer.wait(10000);
        }

        stopBroker();
        assertTrue(t.passed());
    }

};