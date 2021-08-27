//,temp,JMSClientTest.java,511,525,temp,JMSClientTest.java,453,470
//,3
public class xxx {
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