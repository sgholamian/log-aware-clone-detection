//,temp,DurableSubscriberWithNetworkDisconnectTest.java,92,100,temp,AMQ2084Test.java,125,136
//,3
public class xxx {
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage txtMsg = (TextMessage) message;
                            String msg = txtMsg.getText();
                            LOG.info("Topic Message Received: " + topicName + " - " + msg);
                        }
                        message.acknowledge();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

};