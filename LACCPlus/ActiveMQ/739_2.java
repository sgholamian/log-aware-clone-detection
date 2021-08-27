//,temp,JMSConsumerTest.java,533,547,temp,AMQ1893Test.java,138,153
//,3
public class xxx {
                public void onMessage(Message message) {

                    if (debug) {
                        try {
                            log.info(((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }

                    if (counter.incrementAndGet() == totalMessageCount) {

                            finishLatch.countDown();

                    }
                }

};