//,temp,sample_2402.java,2,13,temp,sample_2403.java,2,14
//,2
public class xxx {
protected void processRollback(Message mail, Exchange exchange) {
String uid = (String) exchange.removeProperty(MAIL_MESSAGE_UID);
if (getEndpoint().getIdempotentRepository() != null) {
getEndpoint().getIdempotentRepository().remove(uid);
}
Exception cause = exchange.getException();
if (cause != null) {


log.info("exchange failed so rolling back message status");
}
}

};