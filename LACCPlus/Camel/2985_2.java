//,temp,DisruptorEndpoint.java,298,307,temp,DisruptorEndpoint.java,281,296
//,3
public class xxx {
    void onStarted(final DisruptorConsumer consumer) throws Exception {
        synchronized (this) {
            // validate multiple consumers has been enabled is necessary
            if (!consumers.isEmpty() && !isMultipleConsumersSupported()) {
                throw new IllegalStateException(
                        "Multiple consumers for the same endpoint is not allowed: " + this);
            }
            if (consumers.add(consumer)) {
                LOGGER.debug("Starting consumer {} on endpoint {}", consumer, getEndpointUri());
                getDisruptor().reconfigure();
            } else {
                LOGGER.debug("Tried to start Consumer {} on endpoint {} but it was already started", consumer,
                        getEndpointUri());
            }
        }
    }

};