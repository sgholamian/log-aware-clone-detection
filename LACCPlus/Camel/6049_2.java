//,temp,QueueReplyManager.java,52,67,temp,TemporaryQueueReplyManager.java,63,75
//,3
public class xxx {
    @Override
    protected void handleReplyMessage(String correlationID, Message message, Session session) {
        ReplyHandler handler = correlation.get(correlationID);
        if (handler != null) {
            correlation.remove(correlationID);
            handler.onReply(correlationID, message, session);
        } else {
            // we could not correlate the received reply message to a matching request and therefore
            // we cannot continue routing the unknown message
            // log a warn and then ignore the message
            log.warn("Reply received for unknown correlationID [{}]. The message will be ignored: {}", correlationID, message);
        }
    }

};