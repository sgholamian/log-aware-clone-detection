//,temp,sample_3027.java,2,17,temp,sample_3026.java,2,16
//,3
public class xxx {
private synchronized void cleanup(OperationState state) throws HiveSQLException {
setState(state);
if (shouldRunAsync() && state != OperationState.CANCELED && state != OperationState.TIMEDOUT) {
Future<?> backgroundHandle = getBackgroundHandle();
if (backgroundHandle != null) {
boolean success = backgroundHandle.cancel(true);
String queryId = queryState.getQueryId();
if (success) {
} else if (state == OperationState.CANCELED) {


log.info("the running operation could not be cancelled typically because it has already completed normally");
}
}
}
}

};