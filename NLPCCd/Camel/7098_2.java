//,temp,sample_7626.java,2,15,temp,sample_7625.java,2,12
//,3
public class xxx {
protected boolean runningAllowed() {
boolean quickStop = false;
if (isAllowQuickStop() && !endpoint.isAcceptMessagesWhileStopping()) {
quickStop = endpoint.getCamelContext().getStatus().isStopping();
}
if (quickStop) {


log.info("runningallowed false due camelcontext is stopping and endpoint configured to not accept messages while stopping");
}
}

};