//,temp,JmsTopicRequestReplyTest.java,110,142,temp,JMSClientRequestResponseTest.java,171,201
//,3
public class xxx {
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage requestMessage = (TextMessage)message;

            LOG.info("Received request.");
            LOG.info(requestMessage.toString());

            Destination replyDestination = requestMessage.getJMSReplyTo();
            if (replyDestination instanceof Topic) {
                LOG.info("Reply destination is: {}", ((Topic)replyDestination).getTopicName());
            } else {
                LOG.info("Reply destination is: {}", ((Queue)replyDestination).getQueueName());
            }

            TextMessage replyMessage = responderSession.createTextMessage("response for: " + requestMessage.getText());
            replyMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());

            if (dynamicallyCreateProducer) {
                responseProducer = responderSession.createProducer(replyDestination);
                responseProducer.send(replyMessage);
            } else {
                responseProducer.send(replyDestination, replyMessage);
            }

            LOG.info("Sent reply.");
            LOG.info(replyMessage.toString());
        } catch (JMSException e) {
            onException(e);
        }
    }

};