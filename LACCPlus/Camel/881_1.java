//,temp,DefaultConsumerCache.java,129,145,temp,DefaultConsumerCache.java,111,127
//,3
public class xxx {
    @Override
    public Exchange receive(Endpoint endpoint, long timeout) {
        if (camelContext.isStopped()) {
            throw new RejectedExecutionException("CamelContext is stopped");
        }

        LOG.debug("<<<< {}", endpoint);
        PollingConsumer consumer = null;
        try {
            consumer = acquirePollingConsumer(endpoint);
            return consumer.receive(timeout);
        } finally {
            if (consumer != null) {
                releasePollingConsumer(endpoint, consumer);
            }
        }
    }

};