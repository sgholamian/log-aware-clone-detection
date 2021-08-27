//,temp,Tracer.java,302,324,temp,Tracer.java,280,300
//,3
public class xxx {
        @Override
        public void onExchangeBegin(Route route, Exchange exchange) {
            try {
                if (isExcluded(exchange, route.getEndpoint())) {
                    return;
                }
                SpanDecorator sd = getSpanDecorator(route.getEndpoint());
                SpanAdapter parent = ActiveSpanManager.getSpan(exchange);
                SpanAdapter span;
                span = startExchangeBeginSpan(exchange, sd, sd.getOperationName(exchange, route.getEndpoint()),
                        sd.getReceiverSpanKind(), parent);
                sd.pre(span, exchange, route.getEndpoint());
                ActiveSpanManager.activate(exchange, span);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Tracing: start server span={}", span);
                }
            } catch (Exception t) {
                // This exception is ignored
                LOG.warn("Tracing: Failed to capture tracing data", t);
            }
        }

};