//,temp,AtlasMapEndpoint.java,159,163,temp,XsltEndpoint.java,106,110
//,3
public class xxx {
    public AtlasMapEndpoint findOrCreateEndpoint(String uri, String newResourceUri) {
        String newUri = uri.replace(getResourceUri(), newResourceUri);
        log.debug("Getting endpoint with URI: {}", newUri);
        return getCamelContext().getEndpoint(newUri, AtlasMapEndpoint.class);
    }

};