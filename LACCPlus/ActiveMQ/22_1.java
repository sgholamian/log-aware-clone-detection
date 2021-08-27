//,temp,DurableSubscriberWithNetworkDisconnectTest.java,92,100,temp,AMQ2084Test.java,125,136
//,3
public class xxx {
            public void onMessage(Message msg) {
                try {
                    TextMessage textMsg = (TextMessage) msg;
                    receivedMsgs++;
                    LOG.info("Received messages (" + receivedMsgs + "): " + textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

};