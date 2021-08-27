//,temp,ZipkinTracer.java,678,720,temp,ZipkinTracer.java,588,632
//,3
public class xxx {
    private Span serverRequest(Tracing brave, String serviceName, Exchange exchange) {
        // reuse existing span if we do multiple requests from the same
        ExtendedExchange extendedExchange = exchange.adapt(ExtendedExchange.class);
        ZipkinState state = extendedExchange.getSafeCopyProperty(ZipkinState.KEY, ZipkinState.class);
        if (state == null) {
            state = new ZipkinState();
            extendedExchange.setSafeCopyProperty(ZipkinState.KEY, state);
        }
        Span span = null;
        Span.Kind spanKind = getConsumerComponentSpanKind(exchange.getFromEndpoint());
        CamelRequest cr = new CamelRequest(exchange.getIn(), spanKind);
        TraceContextOrSamplingFlags sampleFlag = EXTRACTOR.extract(cr);
        if (ObjectHelper.isEmpty(sampleFlag)) {
            span = brave.tracer().nextSpan();
        } else {
            span = brave.tracer().nextSpan(sampleFlag);
        }
        span.kind(spanKind).start();
        ZipkinServerRequestAdapter parser = new ZipkinServerRequestAdapter(this, exchange);
        parser.onRequest(exchange, span.customizer());
        INJECTOR.inject(span.context(), cr);
        // store span after request
        state.pushServerSpan(span);
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
                LOG.debug(String.format("serverRequest [service=%s, traceId=%20s, spanId=%20s, parentId=%20s]", serviceName,
                        traceId, spanId, parentId));
            } else {
                LOG.debug(String.format("serverRequest [service=%s, traceId=%20s, spanId=%20s]", serviceName, traceId, spanId));
            }
        }

        return span;
    }

};