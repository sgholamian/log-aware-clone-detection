//,temp,sample_7033.java,2,12,temp,sample_7032.java,2,12
//,2
public class xxx {
public void testTemporaryDestinationTypes() throws Exception {
JmsEndpoint endpoint = getMandatoryEndpoint("activemq:test.queue", JmsEndpoint.class);
JmsConfiguration configuration = endpoint.getConfiguration();
JmsProviderMetadata providerMetadata = configuration.getProviderMetadata();
assertNotNull("provider", providerMetadata);
Class<? extends TemporaryQueue> queueType = endpoint.getTemporaryQueueType();
Class<? extends TemporaryTopic> topicType = endpoint.getTemporaryTopicType();


log.info("found queue type");
}

};