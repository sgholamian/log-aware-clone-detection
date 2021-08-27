//,temp,sample_1220.java,2,18,temp,sample_5058.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (failed && !getEndpoint().getConfiguration().isTransferExchange()) {
if (exchange.getException() != null) {
body = exchange.getException();
} else {
body = exchange.getOut().getBody();
}
}
if (body == null) {
noReplyLogger.log("No payload to send as reply for exchange: " + exchange);
if (getEndpoint().getConfiguration().isDisconnectOnNoReply()) {


log.info("closing session as no payload to send as reply at address");
}
}
}

};