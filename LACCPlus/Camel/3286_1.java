//,temp,CordaConsumer.java,235,240,temp,CordaConsumer.java,200,205
//,3
public class xxx {
    private void processError(Throwable throwable, String operation) {
        LOG.debug("processError for operation: " + operation + " " + throwable);
        Exchange exchange = createExchange(true);
        exchange.setException(throwable);
        processEvent(exchange);
    }

};