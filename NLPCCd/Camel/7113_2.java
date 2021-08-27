//,temp,sample_2455.java,2,10,temp,sample_2454.java,2,8
//,3
public class xxx {
public void status(PubNub pubnub, PNStatus status) {
if (status.getCategory() == PNUnexpectedDisconnectCategory || status.getCategory() == PNTimeoutCategory) {


log.info("got status reconnecting to pubnub");
}
}

};