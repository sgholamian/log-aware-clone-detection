//,temp,sample_5695.java,2,10,temp,sample_1352.java,2,9
//,3
public class xxx {
private void addDelay(SendMessageRequest request, Exchange exchange) {
Integer headerValue = exchange.getIn().getHeader(SqsConstants.DELAY_HEADER, Integer.class);
Integer delayValue;
if (headerValue == null) {


log.info("using the config delay");
}
}

};