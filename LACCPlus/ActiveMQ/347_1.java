//,temp,TopicBridgeSelectorConduitOnOff.java,107,134,temp,AMQ2584ConcurrentDlqTest.java,140,156
//,3
public class xxx {
                @Override
                public void run() {
                    try {
                        Connection connection = remoteConnectionFactoryForConsumers.createConnection();
                        connection.start();
                        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                        MessageConsumer consumerWithSelector = session.createConsumer(destination, (id%2==0 ? "COLOUR = 'RED'" : "COLOUR = 'BLUE'"));
                        consumerWithSelector.setMessageListener(new MessageListener() {
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
                        });
                        connections.add(connection);
                        consumersRegistered.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

};