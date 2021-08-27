//,temp,MinioConsumer.java,386,393,temp,AWS2S3Consumer.java,347,354
//,3
public class xxx {
    protected void processRollback(Exchange exchange) {
        Exception cause = exchange.getException();
        if (isNotEmpty(cause)) {
            LOG.warn("Exchange failed, so rolling back message status: {}", exchange, cause);
        } else {
            LOG.warn("Exchange failed, so rolling back message status: {}", exchange);
        }
    }

};