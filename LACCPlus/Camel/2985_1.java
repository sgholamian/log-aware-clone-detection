//,temp,DisruptorEndpoint.java,298,307,temp,DisruptorEndpoint.java,281,296
//,3
public class xxx {
    void onStopped(final DisruptorConsumer consumer) throws Exception {
        synchronized (this) {
            if (consumers.remove(consumer)) {
                LOGGER.debug("Stopping consumer {} on endpoint {}", consumer, getEndpointUri());
                getDisruptor().reconfigure();
            } else {
                LOGGER.debug("Tried to stop Consumer {} on endpoint {} but it was already stopped", consumer, getEndpointUri());
            }
        }
    }

};