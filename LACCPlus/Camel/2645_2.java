//,temp,ScheduledRoutePolicy.java,145,151,temp,ScheduledRoutePolicy.java,137,143
//,2
public class xxx {
    public void pauseRouteTrigger(Action action, String routeId) throws SchedulerException {
        TriggerKey triggerKey = retrieveTriggerKey(action, routeId);

        getScheduler().pauseTrigger(triggerKey);

        LOG.debug("Scheduled trigger: {} is paused", triggerKey);
    }

};