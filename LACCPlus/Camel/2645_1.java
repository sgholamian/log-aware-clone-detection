//,temp,ScheduledRoutePolicy.java,145,151,temp,ScheduledRoutePolicy.java,137,143
//,2
public class xxx {
    public void resumeRouteTrigger(Action action, String routeId) throws SchedulerException {
        TriggerKey triggerKey = retrieveTriggerKey(action, routeId);

        getScheduler().resumeTrigger(triggerKey);

        LOG.debug("Scheduled trigger: {} is resumed", triggerKey);
    }

};