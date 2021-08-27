//,temp,ZipkinTracer.java,727,759,temp,ZipkinTracer.java,644,676
//,3
public class xxx {
    private void serverResponse(Tracing brave, String serviceName, Exchange exchange) {
        Span span = null;
        ExtendedExchange extendedExchange = exchange.adapt(ExtendedExchange.class);
        ZipkinState state = extendedExchange.getSafeCopyProperty(ZipkinState.KEY, ZipkinState.class);
        if (state != null) {
            // only process if it was a zipkin server event
            span = state.popServerSpan();
        }

        if (span != null) {
            ZipkinServerResponseAdapter parser = new ZipkinServerResponseAdapter(this, exchange);
            parser.onResponse(exchange, span.customizer());
            span.finish();
            TraceContext context = span.context();
            String traceId = "" + context.traceIdString();
            String spanId = "" + context.spanId();
            String parentId = context.parentId() != null ? "" + context.parentId() : null;
            if (camelContext.isUseMDCLogging()) {
                MDC.put("traceId", traceId);
                MDC.put("spanId", spanId);
                MDC.put("parentId", parentId);
            }
            if (LOG.isDebugEnabled()) {
                if (parentId != null) {
                    LOG.debug(String.format("serverResponse[service=%s, traceId=%20s, spanId=%20s, parentId=%20s]", serviceName,
                            traceId, spanId, parentId));
                } else {
                    LOG.debug(String.format("serverResponse[service=%s, traceId=%20s, spanId=%20s]", serviceName, traceId,
                            spanId));
                }
            }
        }
    }

};