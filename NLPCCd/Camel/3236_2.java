//,temp,sample_8209.java,2,11,temp,sample_5782.java,2,10
//,3
public class xxx {
public void deleteRouteJob(Action action, ScheduledRouteDetails scheduledRouteDetails) throws SchedulerException {
JobKey jobKey = retrieveJobKey(action, scheduledRouteDetails);
if (!getScheduler().isShutdown()) {
getScheduler().deleteJob(jobKey);
}


log.info("scheduled job is deleted");
}

};