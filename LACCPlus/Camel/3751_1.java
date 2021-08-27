//,temp,ThriftConsumer.java,233,247,temp,GrpcConsumer.java,173,187
//,3
public class xxx {
    private boolean doSend(Exchange exchange, AsyncCallback callback) {
        if (isRunAllowed()) {
            getAsyncProcessor().process(exchange, doneSync -> {
                if (exchange.getException() != null) {
                    getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
                }
                callback.done(doneSync);
            });
            return false;
        } else {
            LOG.warn("Consumer not ready to process exchanges. The exchange {} will be discarded", exchange);
            callback.done(true);
            return true;
        }
    }

};