//,temp,QuartzEndpoint.java,602,613,temp,QuartzEndpoint.java,587,600
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
            LOG.info("Pausing trigger {}", triggerKey);
            scheduler.pauseTrigger(triggerKey);
        }
    }

};