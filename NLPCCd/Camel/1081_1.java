//,temp,sample_4112.java,2,17,temp,sample_4111.java,2,13
//,3
public class xxx {
public void dummy_method(){
Destination dest = null;
boolean isPubSub = isTopicPrefix(destinationName) || (!isQueuePrefix(destinationName) && endpoint.isPubSubDomain());
if (endpoint.getDestinationResolver() != null) {
dest = endpoint.getDestinationResolver().resolveDestinationName(session, destinationName, isPubSub);
if (LOG.isDebugEnabled()) {
}
}
if (dest == null) {
String before = destinationName;
destinationName = normalizeDestinationName(destinationName);


log.info("normalized jmsreplyto destination name");
}
}

};