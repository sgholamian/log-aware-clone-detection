//,temp,KameletProducer.java,69,120,temp,DirectProducer.java,67,104
//,3
public class xxx {
    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
        try {
            // we may be forced synchronous
            if (endpoint.isSynchronous()) {
                process(exchange);
                callback.done(true);
                return true;
            }
            if (consumer == null || stateCounter != component.getStateCounter()) {
                stateCounter = component.getStateCounter();
                consumer = component.getConsumer(key, block, timeout);
            }
            if (consumer == null) {
                if (endpoint.isFailIfNoConsumers()) {
                    exchange.setException(new DirectConsumerNotAvailableException(
                            "No consumers available on endpoint: " + endpoint, exchange));
                } else {
                    LOG.debug("message ignored, no consumers available on endpoint: {}", endpoint);
                }
                callback.done(true);
                return true;
            } else {
                // the consumer may be forced synchronous
                if (consumer.getEndpoint().isSynchronous()) {
                    consumer.getProcessor().process(exchange);
                    callback.done(true);
                    return true;
                } else {
                    return consumer.getAsyncProcessor().process(exchange, callback);
                }
            }
        } catch (Exception e) {
            exchange.setException(e);
            callback.done(true);
            return true;
        }
    }

};