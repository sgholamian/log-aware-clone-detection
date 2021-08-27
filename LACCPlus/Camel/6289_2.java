//,temp,IronMQConsumer.java,146,155,temp,QueueConsumer.java,171,180
//,3
public class xxx {
    private void processCommit(final Exchange exchange) {
        try {
            LOG.trace("Deleting message with pop receipt handle {}...",
                    QueueExchangeHeaders.getPopReceiptFromHeaders(exchange));
            queueOperations.deleteMessage(exchange);
        } catch (QueueStorageException ex) {
            getExceptionHandler().handleException("Error occurred during deleting message. This exception is ignored.",
                    exchange, ex);
        }
    }

};