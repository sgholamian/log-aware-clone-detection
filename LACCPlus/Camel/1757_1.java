//,temp,PubNubProducer.java,159,177,temp,PubNubProducer.java,124,144
//,3
public class xxx {
    private void doSetState(Exchange exchange, AsyncCallback callback) {
        Object body = exchange.getIn().getBody();
        if (ObjectHelper.isEmpty(body)) {
            exchange.setException(new CamelException("Can not publish empty message"));
            callback.done(true);
        }
        LOG.debug("Sending setState [{}] to channel [{}]", body, getChannel(exchange));
        endpoint.getPubnub()
                .setPresenceState()
                .channels(Arrays.asList(getChannel(exchange)))
                .state(body)
                .uuid(getUUID(exchange))
                .async(new PNCallback<PNSetStateResult>() {
                    public void onResponse(PNSetStateResult result, PNStatus status) {
                        LOG.debug("Got setState responsee [{}]", result);
                        processMessage(exchange, callback, status, result);
                    }
                });
    }

};