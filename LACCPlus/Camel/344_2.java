//,temp,Web3jConsumer.java,221,226,temp,Web3jConsumer.java,182,188
//,3
public class xxx {
    private void blockObservable(EthBlock x) {
        EthBlock.Block block = x.getBlock();
        LOG.debug("processEthBlock {}", block);
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.getIn().setBody(block);
        processEvent(exchange);
    }

};