//,temp,TagProcessor.java,51,70,temp,SetBaggageProcessor.java,51,70
//,2
public class xxx {
    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
        try {
            OpenTracingSpanAdapter camelSpan = (OpenTracingSpanAdapter) ActiveSpanManager.getSpan(exchange);
            Span span = camelSpan.getOpenTracingSpan();
            if (span != null) {
                String item = expression.evaluate(exchange, String.class);
                span.setBaggageItem(baggageName, item);
            } else {
                LOG.warn("OpenTracing: could not find managed span for exchange={}", exchange);
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