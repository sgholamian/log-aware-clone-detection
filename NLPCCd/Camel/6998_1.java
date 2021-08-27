//,temp,sample_5696.java,2,12,temp,sample_5697.java,2,13
//,3
public class xxx {
private void addDelay(SendMessageRequest request, Exchange exchange) {
Integer headerValue = exchange.getIn().getHeader(SqsConstants.DELAY_HEADER, Integer.class);
Integer delayValue;
if (headerValue == null) {
delayValue = getEndpoint().getConfiguration().getDelaySeconds();
} else {


log.info("using the header delay");
}
}

};