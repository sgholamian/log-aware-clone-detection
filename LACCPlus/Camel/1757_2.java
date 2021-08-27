//,temp,PubNubProducer.java,159,177,temp,PubNubProducer.java,124,144
//,3
public class xxx {
    private void doFire(Exchange exchange, AsyncCallback callback) {
        Object body = exchange.getIn().getBody();
        if (ObjectHelper.isEmpty(body)) {
            exchange.setException(new CamelException("Can not fire empty message"));
            callback.done(true);
        }
        LOG.debug("Sending message [{}] to channel [{}]", body, getChannel(exchange));
        endpoint.getPubnub()
                .fire()
                .message(body)
                .channel(getChannel(exchange))
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        if (!status.isError()) {
                            exchange.getIn().setHeader(PubNubConstants.TIMETOKEN, result.getTimetoken());
                        }
                        processMessage(exchange, callback, status, null);
                    }
                });
    }

};