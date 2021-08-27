//,temp,sample_8205.java,2,18,temp,sample_5778.java,2,18
//,3
public class xxx {
public void scheduleRoute(Action action, Route route) throws Exception {
JobDetail jobDetail = createJobDetail(action, route);
Trigger trigger = createTrigger(action, route);
updateScheduledRouteDetails(action, jobDetail, trigger, route);
loadCallbackDataIntoSchedulerContext(jobDetail, action, route);
boolean isClustered = route.getRouteContext().getCamelContext().getComponent("quartz2", QuartzComponent.class).isClustered();
if (isClustered) {
JobDetail existingJobDetail = getScheduler().getJobDetail(jobDetail.getKey());
if (jobDetail.equals(existingJobDetail)) {
if (LOG.isInfoEnabled()) {


log.info("skipping to schedule the job for action on route as the job already existing inside the cluster");
}
}
}
}

};