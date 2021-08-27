//,temp,sample_5646.java,2,15,temp,sample_5647.java,2,17
//,3
public class xxx {
private void calculateState(Route route) {
boolean failureLimitReached = isThresholdExceeded();
if (state.get() == STATE_CLOSED) {
if (failureLimitReached) {
openCircuit(route);
}
} else if (state.get() == STATE_HALF_OPEN) {
if (failureLimitReached) {


log.info("opening circuit");
}
}
}

};