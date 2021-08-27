//,temp,MessageEvictionTest.java,160,189,temp,MessageEvictionTest.java,109,145
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    ActiveMQTopic discardedAdvisoryDestination =
                        AdvisorySupport.getMessageDiscardedAdvisoryTopic(destination);
                    // use separate session rather than asyncDispatch on consumer session
                    // as we want consumer session to block
                    Session advisorySession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    final MessageConsumer consumer = advisorySession.createConsumer(discardedAdvisoryDestination);
                    consumer.setMessageListener(new MessageListener() {
                        int advisoriesReceived = 0;
                        @Override
                        public void onMessage(Message message) {
                            try {
                                LOG.info("advisory:" + message);
                                ActiveMQMessage activeMQMessage = (ActiveMQMessage) message;
                                assertNotNull(activeMQMessage.getStringProperty(AdvisorySupport.MSG_PROPERTY_CONSUMER_ID));
                                assertEquals(++advisoriesReceived, activeMQMessage.getIntProperty(AdvisorySupport.MSG_PROPERTY_DISCARDED_COUNT));
                                message.acknowledge();
                                advisoryIsGood.countDown();
                            } catch (JMSException e) {
                                e.printStackTrace();
                                fail(e.toString());
                            } finally {
                                gotAdvisory.countDown();
                            }
                        }
                    });
                    consumerRegistered.countDown();
                    gotAdvisory.await(120, TimeUnit.SECONDS);
                    consumer.close();
                    advisorySession.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail(e.toString());
                }
            }

};