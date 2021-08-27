//,temp,SnmpOIDPoller.java,238,248,temp,SipSubscriptionListener.java,50,60
//,3
public class xxx {
    private void dispatchExchange(Object response) {
        LOG.debug("Consumer Dispatching the received notification along the route");
        Exchange exchange = sipSubscriber.createExchange(true);
        exchange.setPattern(ExchangePattern.InOnly);
        exchange.getIn().setBody(response);
        try {
            sipSubscriber.getProcessor().process(exchange);
        } catch (Exception e) {
            sipSubscriber.getExceptionHandler().handleException("Error in consumer while dispatching exchange", e);
        }
    }

};