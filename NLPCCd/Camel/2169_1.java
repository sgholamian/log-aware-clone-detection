//,temp,sample_1467.java,2,15,temp,sample_5107.java,2,15
//,2
public class xxx {
protected void handleReplyMessage(String correlationID, Message message, Session session) {
ReplyHandler handler = correlation.get(correlationID);
if (handler == null && endpoint.isUseMessageIDAsCorrelationID()) {
handler = waitForProvisionCorrelationToBeUpdated(correlationID, message);
}
if (handler != null) {
correlation.remove(correlationID);
handler.onReply(correlationID, message, session);
} else {


log.info("reply received for unknown correlationid on reply destination current correlation map size the message will be ignored");
}
}

};