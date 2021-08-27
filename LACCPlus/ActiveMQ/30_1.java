//,temp,AMQ3405Test.java,148,156,temp,JmsTopicRedeliverTest.java,153,159
//,3
public class xxx {
    protected void makeConsumer() throws JMSException {
        Destination destination = getDestination();
        LOG.info("Consuming from: " + destination);
        if (durableSubscriber) {
            consumer = session.createDurableSubscriber((Topic)destination, destination.toString());
        } else {
            consumer = session.createConsumer(destination);
        }
    }

};