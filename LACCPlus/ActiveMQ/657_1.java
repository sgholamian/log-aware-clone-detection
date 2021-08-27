//,temp,InMemoryJobScheduler.java,358,365,temp,JobSchedulerImpl.java,820,826
//,3
public class xxx {
    private void dispatch(InMemoryJob job) throws IllegalStateException, IOException {
        if (canDispatch()) {
            LOG.debug("Firing: {}", job);
            for (JobListener l : jobListeners) {
                l.scheduledJob(job.getJobId(), new ByteSequence(job.getPayload()));
            }
        }
    }

};