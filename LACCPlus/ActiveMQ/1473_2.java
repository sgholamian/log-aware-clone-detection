//,temp,InMemoryJobSchedulerStore.java,85,102,temp,InMemoryJobSchedulerStore.java,66,83
//,3
public class xxx {
    @Override
    public JobScheduler getJobScheduler(String name) throws Exception {
        this.lock.lock();
        try {
            InMemoryJobScheduler result = this.schedulers.get(name);
            if (result == null) {
                LOG.debug("Creating new in-memory scheduler: {}", name);
                result = new InMemoryJobScheduler(name);
                this.schedulers.put(name, result);
                if (isStarted()) {
                    result.start();
                }
            }
            return result;
        } finally {
            this.lock.unlock();
        }
    }

};