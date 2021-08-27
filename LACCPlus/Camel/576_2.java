//,temp,DockerStatsConsumer.java,77,88,temp,Web3jConsumer.java,168,173
//,3
public class xxx {
    private void ethBlockHashObservable(String x) {
        LOG.debug("processEthBlock {}", x);
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};