//,temp,CordaConsumer.java,242,249,temp,Web3jConsumer.java,204,211
//,3
public class xxx {
    public void processEvent(Exchange exchange) {
        LOG.debug("processEvent {}", exchange);
        try {
            getProcessor().process(exchange);
        } catch (Exception e) {
            getExceptionHandler().handleException("Error processing event", e);
        }
    }

};