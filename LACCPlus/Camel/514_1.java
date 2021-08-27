//,temp,QueueReplyManager.java,39,50,temp,TemporaryQueueReplyManager.java,53,61
//,3
public class xxx {
    @Override
    public void updateCorrelationId(String correlationId, String newCorrelationId, long requestTimeout) {
        log.trace("Updated provisional correlationId [{}] to expected correlationId [{}]", correlationId, newCorrelationId);

        ReplyHandler handler = correlation.remove(correlationId);
        if (handler == null) {
            // should not happen that we can't find the handler
            return;
        }

        correlation.put(newCorrelationId, handler, requestTimeout);
    }

};