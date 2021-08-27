//,temp,NatsProducer.java,122,139,temp,NatsConsumer.java,54,65
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        // try to lookup a pool first based on profile
        ThreadPoolProfile profile
                = this.executorServiceManager.getThreadPoolProfile(NatsConstants.NATS_REQUEST_TIMEOUT_THREAD_PROFILE_NAME);
        if (profile == null) {
            profile = this.executorServiceManager.getDefaultThreadPoolProfile();
        }
        this.scheduler
                = this.executorServiceManager.newScheduledThreadPool(this,
                        NatsConstants.NATS_REQUEST_TIMEOUT_THREAD_PROFILE_NAME, profile);
        super.doStart();
        LOG.debug("Starting Nats Producer");

        LOG.debug("Getting Nats Connection");
        connection = getEndpoint().getConfiguration().getConnection() != null
                ? getEndpoint().getConfiguration().getConnection() : getEndpoint().getConnection();
    }

};