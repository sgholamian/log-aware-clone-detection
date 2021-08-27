//,temp,sample_7626.java,2,15,temp,sample_7625.java,2,12
//,3
public class xxx {
protected boolean runningAllowed() {
boolean quickStop = false;
if (isAllowQuickStop() && !endpoint.isAcceptMessagesWhileStopping()) {
quickStop = endpoint.getCamelContext().getStatus().isStopping();
}
if (quickStop) {
return false;
} else {
boolean answer = endpoint.isRunning();


log.info("runningallowed");
}
}

};