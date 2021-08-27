//,temp,sample_4308.java,2,13,temp,sample_7833.java,2,12
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
if (endpoint.getConsumer() == null) {
if (endpoint.isFailIfNoConsumers()) {
throw new DirectConsumerNotAvailableException("No consumers available on endpoint: " + endpoint, exchange);
} else {


log.info("message ignored no consumers available on endpoint");
}
}
}

};