//,temp,sample_491.java,2,11,temp,sample_492.java,2,15
//,3
public class xxx {
public boolean rollback(Consumer consumer, Endpoint endpoint, int retryCounter, Exception e) throws Exception {
if (consumer instanceof RemoteFileConsumer) {
RemoteFileConsumer<?> rfc = (RemoteFileConsumer<?>) consumer;
if (rfc.isRunAllowed()) {
try {
rfc.forceDisconnect();
} catch (Throwable t) {


log.info("error occurred during force disconnecting from this exception will be ignored");
}
}
}
}

};