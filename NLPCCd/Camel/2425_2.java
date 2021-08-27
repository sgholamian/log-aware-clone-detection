//,temp,sample_5780.java,2,8,temp,sample_5781.java,2,8
//,2
public class xxx {
public void resumeRouteTrigger(Action action, String routeId) throws SchedulerException {
TriggerKey triggerKey = retrieveTriggerKey(action, routeId);
getScheduler().resumeTrigger(triggerKey);


log.info("scheduled trigger is resumed");
}

};