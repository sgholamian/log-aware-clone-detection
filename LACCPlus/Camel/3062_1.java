//,temp,PubNubProducer.java,185,189,temp,PubNubProducer.java,172,175
//,3
public class xxx {
                    @Override
                    public void onResponse(PNGetStateResult result, PNStatus status) {
                        LOG.debug("Got state [{}]", result.getStateByUUID());
                        processMessage(exchange, callback, status, result);
                    }

};