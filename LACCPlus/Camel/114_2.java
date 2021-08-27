//,temp,VelocityEndpoint.java,173,177,temp,FreemarkerEndpoint.java,116,120
//,2
public class xxx {
    public FreemarkerEndpoint findOrCreateEndpoint(String uri, String newResourceUri) {
        String newUri = uri.replace(getResourceUri(), newResourceUri);
        log.debug("Getting endpoint with URI: {}", newUri);
        return getCamelContext().getEndpoint(newUri, FreemarkerEndpoint.class);
    }

};