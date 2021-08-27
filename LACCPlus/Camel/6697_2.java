//,temp,AbstractRestProcessor.java,636,657,temp,FacebookPropertiesHelper.java,110,120
//,3
public class xxx {
    public static Map<String, Object> getExchangeProperties(Exchange exchange, Map<String, Object> properties) {
        int nProperties = 0;
        for (Map.Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
            if (entry.getKey().startsWith(FacebookConstants.FACEBOOK_PROPERTY_PREFIX)) {
                properties.put(entry.getKey().substring(FacebookConstants.FACEBOOK_PROPERTY_PREFIX.length()), entry.getValue());
                nProperties++;
            }
        }
        LOG.debug("Found {} properties in exchange", nProperties);
        return properties;
    }

};