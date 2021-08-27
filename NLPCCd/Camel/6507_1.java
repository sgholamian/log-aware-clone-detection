//,temp,sample_8265.java,2,14,temp,sample_6340.java,2,14
//,3
public class xxx {
private void sendResponse(MessageEvent messageEvent, Exchange exchange) throws Exception {
Object body = getResponseBody(exchange);
if (body == null) {
noReplyLogger.log("No payload to send as reply for exchange: " + exchange);
if (consumer.getConfiguration().isDisconnectOnNoReply()) {
if (LOG.isTraceEnabled()) {


log.info("closing channel as no payload to send as reply at address");
}
}
}
}

};