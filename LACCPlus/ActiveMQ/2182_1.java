//,temp,JmsProducerClient.java,326,342,temp,AbstractJmsClient.java,238,251
//,3
public class xxx {
    @Override
    protected Destination createTemporaryDestination(String destName) throws JMSException {
        String simpleName = getSimpleName(destName);
        byte destinationType = getDestinationType(destName);

        // when we produce to temp destinations, we publish to them as
        // though they were normal queues or topics
        if (destinationType == ActiveMQDestination.TEMP_QUEUE_TYPE) {
            LOG.info("Creating queue: {}", destName);
            return getSession().createQueue(simpleName);
        } else if (destinationType == ActiveMQDestination.TEMP_TOPIC_TYPE) {
            LOG.info("Creating topic: {}", destName);
            return getSession().createTopic(simpleName);
        } else {
            throw new IllegalArgumentException("Unrecognized destination type: " + destinationType);
        }
    }

};