//,temp,sample_2942.java,2,9,temp,sample_2943.java,2,10
//,2
public class xxx {
protected void processRollback(Exchange exchange) {
Exception cause = exchange.getException();
if (cause != null) {


log.info("exchange failed so rolling back message status");
}
}

};