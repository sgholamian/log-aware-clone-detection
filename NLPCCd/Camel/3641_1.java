//,temp,sample_5309.java,2,11,temp,sample_5308.java,2,9
//,3
public class xxx {
private void doAddJob(JobDetail job, Trigger trigger) throws SchedulerException {
Trigger existingTrigger = getScheduler().getTrigger(trigger.getName(), trigger.getGroup());
if (existingTrigger == null) {
getScheduler().scheduleJob(job, trigger);
} else if (hasTriggerChanged(existingTrigger, trigger)) {


log.info("trigger already exists and will be updated by quartz");
}
}

};