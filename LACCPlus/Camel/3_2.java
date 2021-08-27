//,temp,Web3jConsumer.java,197,202,temp,Web3jConsumer.java,190,195
//,2
public class xxx {
    private void processTransaction(Transaction x) {
        LOG.debug("processTransaction {}", x);
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};