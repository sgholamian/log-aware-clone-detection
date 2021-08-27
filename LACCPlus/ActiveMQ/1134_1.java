//,temp,JmsTopicRequestReplyTest.java,147,158,temp,JMSClientRequestResponseTest.java,158,169
//,2
public class xxx {
    protected void syncConsumeLoop(MessageConsumer requestConsumer) {
        try {
            Message message = requestConsumer.receive(5000);
            if (message != null) {
                onMessage(message);
            } else {
                LOG.error("No message received");
            }
        } catch (JMSException e) {
            onException(e);
        }
    }

};