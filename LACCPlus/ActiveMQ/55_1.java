//,temp,TopicBridgeSelectorConduitOnOff.java,116,127,temp,AMQ4351Test.java,94,104
//,3
public class xxx {
                            @Override
                            public void onMessage(Message message) {
                                int messageCount = receivedCount.incrementAndGet();
                                allReceived.countDown();
                                if (messageCount % 20000 == 0) {
                                    try {
                                        LOG.info("Consumer id: " + id + ", message COLOUR:" + message.getStringProperty("COLOUR"));
                                    } catch (JMSException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

};