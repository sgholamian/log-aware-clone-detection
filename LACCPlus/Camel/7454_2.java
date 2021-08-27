//,temp,CxfHeaderHelper.java,121,168,temp,CxfHeaderHelper.java,76,111
//,3
public class xxx {
    public static void propagateCamelHeadersToCxfHeaders(
            HeaderFilterStrategy strategy,
            Map<String, Object> camelHeaders, Map<String, List<String>> requestHeaders,
            Exchange camelExchange)
            throws Exception {
        if (strategy == null) {
            return;
        }
        camelHeaders.entrySet().forEach(entry -> {
            // Need to make sure the cxf needed header will not be filtered
            if (strategy.applyFilterToCamelHeaders(entry.getKey(), entry.getValue(), camelExchange)
                    && CAMEL_TO_CXF_HEADERS.get(entry.getKey()) == null) {
                LOG.trace("Drop Camel header: {}={}", entry.getKey(), entry.getValue());
                return;
            }

            // drop this header as we do not want to propagate the http method/path into the CXF request message
            if (Exchange.HTTP_METHOD.equalsIgnoreCase(entry.getKey())
                    || Exchange.HTTP_PATH.equalsIgnoreCase(entry.getKey())
                    || Exchange.HTTP_QUERY.equalsIgnoreCase(entry.getKey())) {
                return;
            }

            // we need to make sure the entry value is not null
            if (entry.getValue() == null) {
                LOG.trace("Drop Camel header: {}={}", entry.getKey(), entry.getValue());
                return;
            }

            String cxfHeaderName = CAMEL_TO_CXF_HEADERS.getOrDefault(entry.getKey(), entry.getKey());

            LOG.trace("Propagate Camel header: {}={} as {}", entry.getKey(), entry.getValue(), cxfHeaderName);

            requestHeaders.put(cxfHeaderName, Arrays.asList(entry.getValue().toString()));
        });
    }

};