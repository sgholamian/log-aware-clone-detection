//,temp,ProducerEventSource.java,107,119,temp,ConsumerEventSource.java,116,128
//,2
public class xxx {
    protected int extractConsumerCountFromMessage(Message message, int count) {
        try {
            Object value = message.getObjectProperty("consumerCount");
            if (value instanceof Number) {
                Number n = (Number)value;
                return n.intValue();
            }
            LOG.warn("No consumerCount header available on the message: " + message);
        } catch (Exception e) {
            LOG.warn("Failed to extract consumerCount from message: " + message + ".Reason: " + e, e);
        }
        return count;
    }

};