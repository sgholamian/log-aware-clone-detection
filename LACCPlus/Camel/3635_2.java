//,temp,GetCorrelationContextProcessor.java,44,62,temp,AttributeProcessor.java,51,70
//,3
public class xxx {
    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
        try {
            OpenTelemetrySpanAdapter camelSpan = (OpenTelemetrySpanAdapter) ActiveSpanManager.getSpan(exchange);
            Span span = camelSpan.getOpenTelemetrySpan();
            if (span != null) {
                String tag = expression.evaluate(exchange, String.class);
                span.setAttribute(attributeName, tag);
            } else {
                LOG.warn("OpenTelemetry: could not find managed span for exchange={}", exchange);
            }
        } catch (Exception e) {
            exchange.setException(e);
        } finally {
            // callback must be invoked
            callback.done(true);
        }

        return true;
    }

};