//,temp,sample_7078.java,2,15,temp,sample_7084.java,2,14
//,3
public class xxx {
public void pauseTrigger() throws Exception {
Scheduler scheduler = getComponent().getScheduler();
boolean isClustered = scheduler.getMetaData().isJobStoreClustered();
if (jobPaused.get() || isClustered) {
return;
}
jobPaused.set(true);
if (!scheduler.isShutdown()) {


log.info("pausing trigger");
}
}

};