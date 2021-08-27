//,temp,ZipkinTracer.java,727,759,temp,ZipkinTracer.java,644,676
//,3
public class xxx {
    private void clientResponse(Tracing brave, String serviceName, ExchangeSentEvent event) {
        Span span = null;
        ExtendedExchange exchange = event.getExchange().adapt(ExtendedExchange.class);
        ZipkinState state = exchange.getSafeCopyProperty(ZipkinState.KEY, ZipkinState.class);
        if (state != null) {
            // only process if it was a zipkin client event
            span = state.popClientSpan();
        }

        if (span != null) {
            ZipkinClientResponseAdaptor parser = new ZipkinClientResponseAdaptor(this, event.getEndpoint());
            parser.onResponse(event.getExchange(), span.customizer());
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
                    LOG.debug(String.format("clientResponse[service=%s, traceId=%20s, spanId=%20s, parentId=%20s]", serviceName,
                            traceId, spanId, parentId));
                } else {
                    LOG.debug(String.format("clientResponse[service=%s, traceId=%20s, spanId=%20s]", serviceName, traceId,
                            spanId));
                }
            }
        }
    }

};