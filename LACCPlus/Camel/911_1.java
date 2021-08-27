//,temp,PubNubProducer.java,208,219,temp,PubNubProducer.java,179,191
//,3
public class xxx {
    private void doWhereNow(Exchange exchange, AsyncCallback callback) {
        endpoint.getPubnub()
                .whereNow()
                .uuid(getUUID(exchange))
                .async(new PNCallback<PNWhereNowResult>() {
                    @Override
                    public void onResponse(PNWhereNowResult result, PNStatus status) {
                        LOG.debug("Got whereNow message [{}]", result.getChannels());
                        processMessage(exchange, callback, status, result.getChannels());
                    }
                });
    }

};