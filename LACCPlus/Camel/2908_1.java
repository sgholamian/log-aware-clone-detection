//,temp,Sqs2Producer.java,237,249,temp,Sqs2Producer.java,223,235
//,2
public class xxx {
    private void addDelay(SendMessageBatchRequestEntry.Builder request, Exchange exchange) {
        Integer headerValue = exchange.getIn().getHeader(Sqs2Constants.DELAY_HEADER, Integer.class);
        Integer delayValue;
        if (headerValue == null) {
            LOG.trace("Using the config delay");
            delayValue = getEndpoint().getConfiguration().getDelaySeconds();
        } else {
            LOG.trace("Using the header delay");
            delayValue = headerValue;
        }
        LOG.trace("found delay: {}", delayValue);
        request.delaySeconds(delayValue == null ? Integer.valueOf(0) : delayValue);
    }

};