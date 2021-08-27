//,temp,JmsProducerClient.java,326,342,temp,AbstractJmsClient.java,238,251
//,3
public class xxx {
    protected Destination createDestination(String destName) throws JMSException {
        String simpleName = getSimpleName(destName);
        byte destinationType = getDestinationType(destName);

        if (destinationType == ActiveMQDestination.QUEUE_TYPE) {
            LOG.info("Creating queue: {}", destName);
            return getSession().createQueue(simpleName);
        } else if (destinationType == ActiveMQDestination.TOPIC_TYPE) {
            LOG.info("Creating topic: {}", destName);
            return getSession().createTopic(simpleName);
        } else {
            return createTemporaryDestination(destName);
        }
    }

};