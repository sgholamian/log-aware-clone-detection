//,temp,sample_4544.java,2,18,temp,sample_4543.java,2,18
//,3
public class xxx {
public void dummy_method(){
setEndpointHeaderFilterStrategy(endpoint);
endpoint.setClient(getClient());
endpoint.setClientConfig(getClientConfig());
endpoint.setBinding(getBinding());
endpoint.setSslContextParameters(ssl);
setProperties(endpoint, parameters);
if (IntrospectionSupport.hasProperties(parameters, CLIENT_CONFIG_PREFIX)) {
DefaultAsyncHttpClientConfig.Builder builder = endpoint.getClientConfig() == null ? new DefaultAsyncHttpClientConfig.Builder() : AhcComponent.cloneConfig(endpoint.getClientConfig());
if (endpoint.getClient() != null) {
} else if (endpoint.getClientConfig() != null) {


log.info("the user explicitly set an asynchttpclientconfig instance on the component or endpoint but this endpoint uri contains client configuration parameters are you sure that this is what was intended the uri parameters will be applied to a clone of the supplied asynchttpclientconfig in order to prevent unintended modification of the explicitly configured asynchttpclientconfig that is the uri parameters override the settings on the explicitly configured asynchttpclientconfig for this endpoint");
}
}
}

};