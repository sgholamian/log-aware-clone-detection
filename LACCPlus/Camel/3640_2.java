//,temp,RabbitMQProducer.java,379,393,temp,JmsProducer.java,124,138
//,3
public class xxx {
    protected void unInitReplyManager() {
        try {
            if (replyManager != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Stopping JmsReplyManager: {} from processing replies from: {}", replyManager,
                            endpoint.getReplyTo() != null ? endpoint.getReplyTo() : "temporary queue");
                }
                ServiceHelper.stopService(replyManager);
            }
        } catch (Exception e) {
            throw RuntimeCamelException.wrapRuntimeCamelException(e);
        } finally {
            started.set(false);
        }
    }

};