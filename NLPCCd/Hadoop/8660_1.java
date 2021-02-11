//,temp,sample_8613.java,2,17,temp,sample_8266.java,2,12
//,3
public class xxx {
protected final void noteFailure(Exception exception) {
if (LOG.isDebugEnabled()) {
}
if (exception == null) {
return;
}
synchronized (this) {
if (failureCause == null) {
failureCause = exception;
failureState = getServiceState();


log.info("service failed in state cause");
}
}
}

};