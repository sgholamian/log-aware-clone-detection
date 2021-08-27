//,temp,ZipkinTracer.java,678,720,temp,ZipkinTracer.java,588,632
//,3
public class xxx {
    private void clientRequest(Tracing brave, String serviceName, ExchangeSendingEvent event) {
        // reuse existing span if we do multiple requests from the same
        ExtendedExchange exchange = event.getExchange().adapt(ExtendedExchange.class);
        ZipkinState state = exchange.getSafeCopyProperty(ZipkinState.KEY, ZipkinState.class);
        if (state == null) {
            state = new ZipkinState();
            exchange.setSafeCopyProperty(ZipkinState.KEY, state);
        }
        // if we started from a server span then lets reuse that when we call a
        // downstream service
        Span last = state.peekServerSpan();
        Span span;
        if (last != null) {
            span = brave.tracer().newChild(last.context());
        } else {
            span = brave.tracer().nextSpan();
        }
        Span.Kind spanKind = getProducerComponentSpanKind(event.getEndpoint());
        span.kind(spanKind).start();

        ZipkinClientRequestAdapter parser = new ZipkinClientRequestAdapter(this, event.getEndpoint());
        CamelRequest request = new CamelRequest(event.getExchange().getIn(), spanKind);
        INJECTOR.inject(span.context(), request);
        parser.onRequest(event.getExchange(), span.customizer());

        // store span after request
        state.pushClientSpan(span);
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
                LOG.debug(String.format("clientRequest [service=%s, traceId=%20s, spanId=%20s, parentId=%20s]", serviceName,
                        traceId, spanId, parentId));
            } else {
                LOG.debug(String.format("clientRequest [service=%s, traceId=%20s, spanId=%20s]", serviceName, traceId, spanId));
            }
        }
    }

};