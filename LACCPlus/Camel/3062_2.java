//,temp,PubNubProducer.java,185,189,temp,PubNubProducer.java,172,175
//,3
public class xxx {
                    public void onResponse(PNSetStateResult result, PNStatus status) {
                        LOG.debug("Got setState responsee [{}]", result);
                        processMessage(exchange, callback, status, result);
                    }

};