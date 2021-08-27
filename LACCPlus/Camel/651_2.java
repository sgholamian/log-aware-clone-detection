//,temp,GenericFilePollingConsumer.java,220,224,temp,ScheduledPollConsumer.java,263,267
//,3
public class xxx {
    protected void processEmptyMessage() throws Exception {
        Exchange exchange = getEndpoint().createExchange();
        LOG.debug("Sending empty message as there were no messages from polling: {}", this.getEndpoint());
        getProcessor().process(exchange);
    }

};