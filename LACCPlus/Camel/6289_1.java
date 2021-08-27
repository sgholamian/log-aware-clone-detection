//,temp,IronMQConsumer.java,146,155,temp,QueueConsumer.java,171,180
//,3
public class xxx {
    protected void processCommit(Exchange exchange, String messageid, String reservationId) {
        try {
            LOG.trace("Deleting message with messageId {} and reservationId {}...", messageid, reservationId);
            this.ironQueue.deleteMessage(messageid, reservationId);
            LOG.trace("Message deleted");
        } catch (Exception e) {
            getExceptionHandler().handleException("Error occurred during delete of message. This exception is ignored.",
                    exchange, e);
        }
    }

};