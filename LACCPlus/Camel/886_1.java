//,temp,DefaultEndpoint.java,217,230,temp,RemoteFileEndpoint.java,163,177
//,3
public class xxx {
    @Override
    public PollingConsumer createPollingConsumer() throws Exception {
        // should not call configurePollingConsumer when its EventDrivenPollingConsumer
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating EventDrivenPollingConsumer with queueSize: {} blockWhenFull: {} blockTimeout: {} copy: {}",
                    getPollingConsumerQueueSize(), isPollingConsumerBlockWhenFull(), getPollingConsumerBlockTimeout(),
                    isPollingConsumerCopy());
        }
        EventDrivenPollingConsumer consumer = new EventDrivenPollingConsumer(this, getPollingConsumerQueueSize());
        consumer.setBlockWhenFull(isPollingConsumerBlockWhenFull());
        consumer.setBlockTimeout(getPollingConsumerBlockTimeout());
        consumer.setCopy(isPollingConsumerCopy());
        return consumer;
    }

};