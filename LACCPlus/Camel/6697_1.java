//,temp,AbstractRestProcessor.java,636,657,temp,FacebookPropertiesHelper.java,110,120
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private Map<String, Object> getQueryParams(Exchange exchange) {

        // use endpoint map
        Map<String, Object> queryParams = new HashMap<>(endpoint.getConfiguration().getApexQueryParams());

        // look for individual properties, allowing endpoint properties to be
        // overridden
        for (Map.Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
            if (entry.getKey().startsWith(APEX_QUERY_PARAM_PREFIX)) {
                queryParams.put(entry.getKey().substring(APEX_QUERY_PARAM_PREFIX.length()), entry.getValue());
            }
        }
        // add params from body if it's a map
        final Object body = exchange.getIn().getBody();
        if (body instanceof Map) {
            queryParams.putAll((Map<String, Object>) body);
        }

        log.debug("Using APEX query params {}", queryParams);
        return queryParams;
    }

};