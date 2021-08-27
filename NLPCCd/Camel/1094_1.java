//,temp,sample_1497.java,2,10,temp,sample_1094.java,2,12
//,3
public class xxx {
protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
ObjectHelper.notNull(getCamelContext(), "Camel Context");
AbstractIgniteEndpoint answer = null;
URI remainingUri = new URI(URISupport.normalizeUri(remaining));
String scheme = remainingUri.getScheme();


log.info("the scheme syntax ignite has been deprecated use ignite instead");
}

};