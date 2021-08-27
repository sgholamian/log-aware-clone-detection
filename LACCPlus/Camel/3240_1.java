//,temp,GuavaEventBusProducer.java,41,50,temp,PassthroughProcessor.java,37,46
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();
        if (body != null) {
            LOG.debug("Posting: {} to EventBus: {}", body, eventBus);
            eventBus.post(body);
        } else {
            LOG.debug("Body is null, cannot post to EventBus");
        }
    }

};