//,temp,DefaultEndpoint.java,217,230,temp,RemoteFileEndpoint.java,163,177
//,3
public class xxx {
    @Override
    public PollingConsumer createPollingConsumer() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating GenericFilePollingConsumer with queueSize: {} blockWhenFull: {} blockTimeout: {}",
                    getPollingConsumerQueueSize(), isPollingConsumerBlockWhenFull(),
                    getPollingConsumerBlockTimeout());
        }
        GenericFilePollingConsumer result = new GenericFilePollingConsumer(this);
        // should not call configurePollingConsumer when its
        // GenericFilePollingConsumer
        result.setBlockWhenFull(isPollingConsumerBlockWhenFull());
        result.setBlockTimeout(getPollingConsumerBlockTimeout());

        return result;
    }

};