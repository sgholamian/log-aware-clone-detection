//,temp,sample_5319.java,2,18,temp,sample_5318.java,2,14
//,3
public class xxx {
public void startScheduler() throws SchedulerException {
for (JobToAdd add : jobsToAdd) {
doAddJob(add.getJob(), add.getTrigger());
}
jobsToAdd.clear();
if (!getScheduler().isStarted()) {
if (getStartDelayedSeconds() > 0) {
try {
getScheduler().startDelayed(getStartDelayedSeconds());
} catch (NoSuchMethodError e) {


log.info("your version of quartz is too old to support delayed startup starting quartz scheduler immediately");
}
}
}
}

};