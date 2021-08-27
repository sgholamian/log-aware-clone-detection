//,temp,InMemoryJobSchedulerStore.java,55,64,temp,InMemoryJobSchedulerStore.java,44,53
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        for (InMemoryJobScheduler scheduler : schedulers.values()) {
            try {
                scheduler.start();
            } catch (Exception e) {
                LOG.error("Failed to start scheduler: {}", scheduler.getName(), e);
            }
        }
    }

};