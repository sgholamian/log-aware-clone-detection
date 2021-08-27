//,temp,Web3jConsumer.java,197,202,temp,Web3jConsumer.java,190,195
//,2
public class xxx {
    private void ethLogObservable(Log x) {
        LOG.debug("processLogObservable {}", x);
        Exchange exchange = this.getEndpoint().createExchange();
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};