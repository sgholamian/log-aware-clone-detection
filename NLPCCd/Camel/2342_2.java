//,temp,sample_8208.java,2,9,temp,sample_8207.java,2,9
//,2
public class xxx {
public void pauseRouteTrigger(Action action, String routeId) throws SchedulerException {
String triggerName = retrieveTriggerName(action, routeId);
String triggerGroup = retrieveTriggerGroup(action, routeId);
getScheduler().pauseTrigger(triggerName, triggerGroup);


log.info("scheduled trigger is paused");
}

};