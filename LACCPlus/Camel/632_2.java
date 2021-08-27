//,temp,CordaConsumer.java,214,219,temp,Web3jConsumer.java,175,180
//,3
public class xxx {
    private void ethPendingTransactionHashObservable(String x) {
        LOG.debug("processEthBlock {}", x);
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};