//,temp,JmsSendReceiveWithMessageExpirationTest.java,294,300,temp,JmsTopicSendReceiveTest.java,107,113
//,2
public class xxx {
    protected MessageConsumer createConsumer() throws JMSException {
        if (durable) {
            LOG.info("Creating durable consumer");
            return session.createDurableSubscriber((Topic)consumerDestination, getName());
        }
        return session.createConsumer(consumerDestination);
    }

};