//,temp,XsltAggregationStrategy.java,178,186,temp,XsltEndpoint.java,320,330
//,3
public class xxx {
    protected void loadResource(String resourceUri) throws TransformerException, IOException {
        LOG.trace("{} loading schema resource: {}", this, resourceUri);
        Source source = xslt.getUriResolver().resolve(resourceUri, null);
        if (source == null) {
            throw new IOException("Cannot load schema resource " + resourceUri);
        } else {
            xslt.setTransformerSource(source);
        }
    }

};