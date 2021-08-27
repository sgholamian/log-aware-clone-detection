//,temp,AtlasMapEndpoint.java,159,163,temp,XsltEndpoint.java,106,110
//,3
public class xxx {
    public XsltEndpoint findOrCreateEndpoint(String uri, String newResourceUri) {
        String newUri = uri.replace(resourceUri, newResourceUri);
        LOG.trace("Getting endpoint with URI: {}", newUri);
        return getCamelContext().getEndpoint(newUri, XsltEndpoint.class);
    }

};