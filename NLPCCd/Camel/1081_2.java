//,temp,sample_4112.java,2,17,temp,sample_4111.java,2,13
//,3
public class xxx {
protected Destination resolveOrCreateDestination(String destinationName, Session session) throws JMSException {
Destination dest = null;
boolean isPubSub = isTopicPrefix(destinationName) || (!isQueuePrefix(destinationName) && endpoint.isPubSubDomain());
if (endpoint.getDestinationResolver() != null) {
dest = endpoint.getDestinationResolver().resolveDestinationName(session, destinationName, isPubSub);
if (LOG.isDebugEnabled()) {


log.info("resolved jmsreplyto destination using destinationresolver as pubsubdomain");
}
}
}

};