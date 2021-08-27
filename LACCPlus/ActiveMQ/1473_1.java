//,temp,InMemoryJobSchedulerStore.java,85,102,temp,InMemoryJobSchedulerStore.java,66,83
//,3
public class xxx {
    @Override
    public boolean removeJobScheduler(String name) throws Exception {
        boolean result = false;

        this.lock.lock();
        try {
            InMemoryJobScheduler scheduler = this.schedulers.remove(name);
            result = scheduler != null;
            if (result) {
                LOG.debug("Removing in-memory Job Scheduler: {}", name);
                scheduler.stop();
                this.schedulers.remove(name);
            }
        } finally {
            this.lock.unlock();
        }
        return result;
    }

};