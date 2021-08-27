//,temp,sample_5355.java,2,17,temp,sample_5356.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (destination != null && sendReply && !endpoint.isReplyToSameDestinationAllowed() && destination.equals(replyDestination)) {
sendReply = false;
}
final Exchange exchange = createExchange(message, session, replyDestination);
if (eagerLoadingOfProperties) {
exchange.getIn().getHeaders();
}
String correlationId = message.getJMSCorrelationID();
if (correlationId != null) {
}


log.info("onmessage process start");
}

};