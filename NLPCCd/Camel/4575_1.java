//,temp,sample_5341.java,2,10,temp,sample_5340.java,2,9
//,2
public class xxx {
protected void processRollback(Exchange exchange) {
Exception cause = exchange.getException();
if (cause != null) {
} else {


log.info("exchange failed so rolling back message status");
}
}

};