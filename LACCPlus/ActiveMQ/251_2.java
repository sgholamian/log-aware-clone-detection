//,temp,TopicProducerFlowControlTest.java,163,182,temp,ExpiredMessagesWithNoConsumerTest.java,418,433
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
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }

};