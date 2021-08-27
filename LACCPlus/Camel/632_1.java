//,temp,CordaConsumer.java,214,219,temp,Web3jConsumer.java,175,180
//,3
public class xxx {
    private void proceedNetworkMapFeed(NetworkMapCache.MapChange x) {
        LOG.debug("proceedNetworkMapFeed {}", x);
        Exchange exchange = createExchange(true);
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};