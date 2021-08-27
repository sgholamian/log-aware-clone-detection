//,temp,sample_6717.java,2,17,temp,sample_6716.java,2,17
//,3
public class xxx {
public void onFailure(Exchange exchange) {
Integer deadline = 0;
Object configuredDeadline = exchange.getIn().getHeader(GooglePubsubConstants.ACK_DEADLINE);
if (configuredDeadline != null && Integer.class.isInstance(configuredDeadline)) {
deadline = (Integer) configuredDeadline;
}
if (configuredDeadline != null && String.class.isInstance(configuredDeadline)) {
try {
deadline = Integer.valueOf((String) configuredDeadline);
} catch (Exception e) {


log.info("unable to parse ack deadline header value");
}
}
}

};