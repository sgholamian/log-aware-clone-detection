//,temp,sample_4544.java,2,18,temp,sample_4543.java,2,18
//,3
public class xxx {
public void dummy_method(){
AhcEndpoint endpoint = createAhcEndpoint(uri, this, null);
setEndpointHeaderFilterStrategy(endpoint);
endpoint.setClient(getClient());
endpoint.setClientConfig(getClientConfig());
endpoint.setBinding(getBinding());
endpoint.setSslContextParameters(ssl);
setProperties(endpoint, parameters);
if (IntrospectionSupport.hasProperties(parameters, CLIENT_CONFIG_PREFIX)) {
DefaultAsyncHttpClientConfig.Builder builder = endpoint.getClientConfig() == null ? new DefaultAsyncHttpClientConfig.Builder() : AhcComponent.cloneConfig(endpoint.getClientConfig());
if (endpoint.getClient() != null) {


log.info("the user explicitly set an asynchttpclient instance on the component or endpoint but this endpoint uri contains client configuration parameters are you sure that this is what was intended the asynchttpclient will be used and the uri parameters will be ignored");
}
}
}

};