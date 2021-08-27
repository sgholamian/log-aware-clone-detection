//,temp,ExpiredMessagesWithNoConsumerTest.java,294,309,temp,ExpiredMessagesWithNoConsumerTest.java,131,151
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    int i = 0;
                    long tStamp = System.currentTimeMillis();
                    while (i++ < sendCount) {
                        producer.send(session.createTextMessage("test"));
                        if (i%100 == 0) {
                            LOG.info("sent: " + i + " @ " + ((System.currentTimeMillis() - tStamp) / 100)  + "m/ms");
                            tStamp = System.currentTimeMillis() ;
                        }

                        if (135 == i) {
                            // allow pending messages to expire, before usage limit kicks in  to flush them
                            TimeUnit.SECONDS.sleep(5);
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }

};