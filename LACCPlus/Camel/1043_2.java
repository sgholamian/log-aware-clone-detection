//,temp,RedeliveryErrorHandler.java,697,719,temp,DelayProcessorSupport.java,155,181
//,3
public class xxx {
    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
        if (!isRunAllowed()) {
            exchange.setException(new RejectedExecutionException("Run is not allowed"));
            callback.done(true);
            return true;
        }

        // calculate delay and wait
        long delay;
        try {
            delay = calculateDelay(exchange);
            if (delay <= 0) {
                // no delay then continue routing
                if (LOG.isTraceEnabled()) {
                    LOG.trace("No delay for exchangeId: {}", exchange.getExchangeId());
                }
                return processor.process(exchange, callback);
            }
        } catch (Throwable e) {
            exchange.setException(e);
            callback.done(true);
            return true;
        }

        return processDelay(exchange, callback, delay);
    }

};