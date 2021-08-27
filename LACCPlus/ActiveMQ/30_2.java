//,temp,AMQ3405Test.java,148,156,temp,JmsTopicRedeliverTest.java,153,159
//,3
public class xxx {
    protected MessageConsumer createConsumer() throws JMSException {
        if (durable) {
            LOG.info("Creating durable consumer");
            return consumeSession.createDurableSubscriber((Topic)consumerDestination, getName());
        }
        return consumeSession.createConsumer(consumerDestination);
    }

};