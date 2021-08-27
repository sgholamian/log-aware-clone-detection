//,temp,AMQ4485LowLimitTest.java,358,378,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,247,266
//,2
public class xxx {
                @Override
                public void onMessage(Message message) {
                    try {
                        if (consumerSleepTime > 0) {
                            TimeUnit.MILLISECONDS.sleep(consumerSleepTime);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        consumerState.accumulator.incrementAndGet();
                        try {
                            consumerState.expected.remove(((ActiveMQMessage) message).getProperty("NUM"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //queueSession.commit();
                    } catch (Exception e) {
                        LOG.error("Failed to commit slow receipt of " + message, e);
                    }
                }

};