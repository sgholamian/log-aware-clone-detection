//,temp,sample_4108.java,2,17,temp,sample_4109.java,2,16
//,3
public class xxx {
public void dummy_method(){
Destination replyTo = null;
String replyToOverride = endpoint.getConfiguration().getReplyToOverride();
if (replyToOverride != null) {
replyTo = resolveOrCreateDestination(replyToOverride, session);
} else if (jmsReplyTo instanceof Destination) {
replyTo = (Destination)jmsReplyTo;
}
if (replyTo != null) {
JmsMessageHelper.setJMSReplyTo(answer, replyTo);
} else {


log.info("not using jmsreplyto");
}
}

};