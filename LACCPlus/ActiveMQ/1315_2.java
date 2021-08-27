//,temp,TopicProducerFlowControlTest.java,258,277,temp,TopicProducerToSubFileCursorTest.java,136,155
//,2
public class xxx {
            public void run() {
                try {
                    for (long i = 0; i < numMessagesToSend; i++) {
                        producer.send(session.createTextMessage("test"));

                        long count = produced.incrementAndGet();
                        if (count % 10000 == 0) {
                            LOG.info("Produced " + count + " messages");
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        producer.close();
                        session.close();
                    } catch (Exception e) {
                    }
                }
            }

};