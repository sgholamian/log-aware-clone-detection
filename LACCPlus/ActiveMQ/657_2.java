//,temp,InMemoryJobScheduler.java,358,365,temp,JobSchedulerImpl.java,820,826
//,3
public class xxx {
    void fireJob(JobLocation job) throws IllegalStateException, IOException {
        LOG.debug("Firing: {}", job);
        ByteSequence bs = this.store.getPayload(job.getLocation());
        for (JobListener l : jobListeners) {
            l.scheduledJob(job.getJobId(), bs);
        }
    }

};