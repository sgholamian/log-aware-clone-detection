//,temp,CxfHeaderHelper.java,121,168,temp,CxfHeaderHelper.java,76,111
//,3
public class xxx {
    public static void propagateCamelToCxf(
            HeaderFilterStrategy strategy,
            Map<String, Object> camelHeaders, Message cxfMessage, Exchange exchange) {

        // use copyProtocolHeadersFromCxfToCamel treemap to keep ordering and ignore key case
        cxfMessage.putIfAbsent(Message.PROTOCOL_HEADERS, new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        final Map<String, List<String>> cxfHeaders = CastUtils.cast((Map<?, ?>) cxfMessage.get(Message.PROTOCOL_HEADERS));

        if (strategy == null) {
            return;
        }

        camelHeaders.entrySet().forEach(entry -> {
            // Need to make sure the cxf needed header will not be filtered
            if (strategy.applyFilterToCamelHeaders(entry.getKey(), entry.getValue(), exchange)) {
                LOG.trace("Drop external header: {}={}", entry.getKey(), entry.getValue());
                return;
            }

            // we need to make sure the entry value is not null
            if (entry.getValue() == null) {
                LOG.trace("Drop Camel header: {}={}", entry.getKey(), entry.getValue());
                return;
            }

            String cxfHeaderName = CAMEL_TO_CXF_HEADERS.getOrDefault(entry.getKey(), entry.getKey());

            LOG.trace("Propagate Camel header: {}={} as {}", entry.getKey(), entry.getValue(), cxfHeaderName);

            if (Exchange.CONTENT_TYPE.equals(entry.getKey())) {
                cxfMessage.put(cxfHeaderName, entry.getValue());
            }
            if (Exchange.HTTP_RESPONSE_CODE.equals(entry.getKey())
                    || Client.REQUEST_CONTEXT.equals(entry.getKey())
                    || Client.RESPONSE_CONTEXT.equals(entry.getKey())) {
                cxfMessage.put(cxfHeaderName, entry.getValue());
            } else {
                Object values = entry.getValue();
                if (values instanceof List<?>) {
                    cxfHeaders.put(cxfHeaderName, CastUtils.cast((List<?>) values, String.class));
                } else {
                    List<String> listValue = new ArrayList<>();
                    listValue.add(entry.getValue().toString());
                    cxfHeaders.put(cxfHeaderName, listValue);
                }
            }
        });
    }

};