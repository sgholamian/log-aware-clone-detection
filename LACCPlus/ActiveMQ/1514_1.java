//,temp,JmsTopicRequestReplyTest.java,110,142,temp,JMSClientRequestResponseTest.java,171,201
//,3
public class xxx {
    public void onMessage(Message message) {
        try {
            TextMessage requestMessage = (TextMessage)message;

            LOG.info("Received request.");
            LOG.info(requestMessage.toString());

            Destination replyDestination = requestMessage.getJMSReplyTo();

            // TODO
            // String value =
            // ActiveMQDestination.getClientId((ActiveMQDestination)
            // replyDestination);
            // assertEquals("clientID from the temporary destination must be the
            // same", clientSideClientID, value);

            TextMessage replyMessage = serverSession.createTextMessage("Hello: " + requestMessage.getText());

            replyMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());

            if (dynamicallyCreateProducer) {
                replyProducer = serverSession.createProducer(replyDestination);
                replyProducer.send(replyMessage);
            } else {
                replyProducer.send(replyDestination, replyMessage);
            }

            LOG.info("Sent reply.");
            LOG.info(replyMessage.toString());
        } catch (JMSException e) {
            onException(e);
        }
    }

};