//,temp,sample_7078.java,2,15,temp,sample_7084.java,2,14
//,3
public class xxx {
private void removeJobInScheduler() throws Exception {
Scheduler scheduler = getComponent().getScheduler();
if (scheduler == null) {
return;
}
if (deleteJob) {
boolean isClustered = scheduler.getMetaData().isJobStoreClustered();
if (!scheduler.isShutdown() && !isClustered) {


log.info("deleting job");
}
}
}

};