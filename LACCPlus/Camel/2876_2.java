//,temp,KameletProducer.java,52,67,temp,DirectProducer.java,50,65
//,2
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        if (consumer == null || stateCounter != component.getStateCounter()) {
            stateCounter = component.getStateCounter();
            consumer = component.getConsumer(key, block, timeout);
        }
        if (consumer == null) {
            if (endpoint.isFailIfNoConsumers()) {
                throw new DirectConsumerNotAvailableException("No consumers available on endpoint: " + endpoint, exchange);
            } else {
                LOG.debug("message ignored, no consumers available on endpoint: {}", endpoint);
            }
        } else {
            consumer.getProcessor().process(exchange);
        }
    }

};