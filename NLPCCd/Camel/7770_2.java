//,temp,sample_6586.java,2,16,temp,sample_2642.java,2,16
//,3
public class xxx {
private boolean doSend(Exchange exchange, AsyncCallback callback) {
if (this.isRunAllowed()) {
this.getAsyncProcessor().process(exchange, doneSync -> {
if (exchange.getException() != null) {
getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
}
callback.done(doneSync);
});
return false;
} else {


log.info("consumer not ready to process exchanges the exchange will be discarded");
}
}

};