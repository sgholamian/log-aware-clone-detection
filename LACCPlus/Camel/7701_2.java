//,temp,StringTemplateEndpoint.java,99,103,temp,MvelEndpoint.java,153,157
//,2
public class xxx {
    public MvelEndpoint findOrCreateEndpoint(String uri, String newResourceUri) {
        String newUri = uri.replace(getResourceUri(), newResourceUri);
        log.debug("Getting endpoint with URI: {}", newUri);
        return getCamelContext().getEndpoint(newUri, MvelEndpoint.class);
    }

};