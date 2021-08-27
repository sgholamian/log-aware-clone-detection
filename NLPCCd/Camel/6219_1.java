//,temp,sample_3913.java,2,17,temp,sample_3914.java,2,17
//,3
public class xxx {
public void dummy_method(){
configuration.setEndpointParameter(endpoint, "concurrentConsumers", 10);
Object concurrentConsumers = configuration.getEndpointParameter(endpoint, "concurrentConsumers");
assertEquals("endpoint.concurrentConsumers", 10, concurrentConsumers);
configuration.setEndpointParameter(endpoint, "size", 1000);
Object size = configuration.getEndpointParameter(endpoint, "size");
assertEquals("endpoint.size", 1000, size);
try {
configuration.setEndpointParameter(endpoint, "doesNotExist", 1000);
fail("Should have got InvalidPropertyException thrown!");
} catch (InvalidPropertyException e) {


log.info("got expected exception");
}
}

};