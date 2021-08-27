//,temp,PubNubProducer.java,208,219,temp,PubNubProducer.java,179,191
//,3
public class xxx {
    private void doGetState(Exchange exchange, AsyncCallback callback) {
        endpoint.getPubnub()
                .getPresenceState()
                .channels(Arrays.asList(getChannel(exchange)))
                .uuid(getUUID(exchange))
                .async(new PNCallback<PNGetStateResult>() {
                    @Override
                    public void onResponse(PNGetStateResult result, PNStatus status) {
                        LOG.debug("Got state [{}]", result.getStateByUUID());
                        processMessage(exchange, callback, status, result);
                    }
                });
    }

};