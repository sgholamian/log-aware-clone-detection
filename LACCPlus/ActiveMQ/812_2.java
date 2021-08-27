//,temp,JMSClientTest.java,511,525,temp,JMSClientTest.java,453,470
//,3
public class xxx {
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