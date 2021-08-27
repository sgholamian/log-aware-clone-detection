//,temp,PubNubProducer.java,151,155,temp,SubscriptionHelper.java,438,443
//,3
public class xxx {
                    @Override
                    public void onResponse(PNHistoryResult result, PNStatus status) {
                        LOG.debug("Got history message [{}]", result);
                        processMessage(exchange, callback, status, result.getMessages());
                    }

};