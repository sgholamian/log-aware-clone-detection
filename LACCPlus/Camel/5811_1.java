//,temp,Tracer.java,302,324,temp,Tracer.java,280,300
//,3
public class xxx {
        @Override
        public void onExchangeDone(Route route, Exchange exchange) {
            try {
                if (isExcluded(exchange, route.getEndpoint())) {
                    return;
                }
                SpanAdapter span = ActiveSpanManager.getSpan(exchange);
                if (span != null) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Tracing: finish server span={}", span);
                    }
                    SpanDecorator sd = getSpanDecorator(route.getEndpoint());
                    sd.post(span, exchange, route.getEndpoint());
                    finishSpan(span);
                    ActiveSpanManager.deactivate(exchange);
                } else {
                    LOG.warn("Tracing: could not find managed span for exchange={}", exchange);
                }
            } catch (Exception t) {
                // This exception is ignored
                LOG.warn("Tracing: Failed to capture tracing data", t);
            }
        }

};