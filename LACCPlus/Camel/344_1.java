//,temp,Web3jConsumer.java,221,226,temp,Web3jConsumer.java,182,188
//,3
public class xxx {
    private void processError(Throwable throwable, String operation) {
        LOG.debug("processError for operation: " + operation + " " + throwable);
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.setException(throwable);
        processEvent(exchange);
    }

};