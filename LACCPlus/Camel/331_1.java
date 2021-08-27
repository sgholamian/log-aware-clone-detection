//,temp,QuartzEndpoint.java,602,613,temp,QuartzEndpoint.java,587,600
//,3
public class xxx {
    public void resumeTrigger() throws Exception {
        if (!jobPaused.get()) {
            return;
        }
        jobPaused.set(false);

        Scheduler scheduler = getComponent().getScheduler();
        if (scheduler != null) {
            LOG.info("Resuming trigger {}", triggerKey);
            scheduler.resumeTrigger(triggerKey);
        }
    }

};