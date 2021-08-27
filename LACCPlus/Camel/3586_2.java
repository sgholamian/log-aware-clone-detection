//,temp,MessageReceiverListenerImpl.java,54,70,temp,AbstractHandler.java,45,62
//,3
public class xxx {
    protected void send(OAIPMHResponse message) {
        Exchange exchange = consumer.createExchange(false);
        String xml = message.getRawResponse();
        exchange.getIn().setBody(xml);
        try {
            // send message to next processor in the route
            LOG.trace("sending exchange: {}", exchange);
            processor.process(exchange);
        } catch (Exception e) {
            exchange.setException(e);
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                exceptionHandler.handleException("Error processing exchange", exchange, exchange.getException());
            }
            consumer.releaseExchange(exchange, false);
        }
    }

};