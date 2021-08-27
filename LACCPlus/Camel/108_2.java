//,temp,IronMQConsumer.java,162,169,temp,GoogleCloudStorageConsumer.java,233,240
//,2
public class xxx {
    protected void processRollback(Exchange exchange) {
        Exception cause = exchange.getException();
        if (cause != null) {
            LOG.warn("Exchange failed, so rolling back message status: {}", exchange, cause);
        } else {
            LOG.warn("Exchange failed, so rolling back message status: {}", exchange);
        }
    }

};