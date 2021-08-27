//,temp,JsltEndpoint.java,125,129,temp,JoltEndpoint.java,167,171
//,2
public class xxx {
    public JsltEndpoint findOrCreateEndpoint(String uri, String newResourceUri) {
        String newUri = uri.replace(getResourceUri(), newResourceUri);
        log.debug("Getting endpoint with URI: {}", newUri);
        return getCamelContext().getEndpoint(newUri, JsltEndpoint.class);
    }

};