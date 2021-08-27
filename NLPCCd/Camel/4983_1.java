//,temp,sample_4308.java,2,13,temp,sample_7833.java,2,12
//,3
public class xxx {
public boolean process(Exchange exchange, AsyncCallback callback) {
DirectVmConsumer consumer = endpoint.getComponent().getConsumer(endpoint);
if (consumer == null) {
if (endpoint.isFailIfNoConsumers()) {
exchange.setException(new DirectVmConsumerNotAvailableException("No consumers available on endpoint: " + endpoint, exchange));
} else {


log.info("message ignored no consumers available on endpoint");
}
}
}

};