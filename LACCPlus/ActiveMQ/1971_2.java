//,temp,InMemoryJobSchedulerStore.java,55,64,temp,InMemoryJobSchedulerStore.java,44,53
//,3
public class xxx {
    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        for (InMemoryJobScheduler scheduler : schedulers.values()) {
            try {
                scheduler.stop();
            } catch (Exception e) {
                LOG.error("Failed to stop scheduler: {}", scheduler.getName(), e);
            }
        }
    }

};