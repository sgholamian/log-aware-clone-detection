//,temp,sample_3910.java,2,17,temp,sample_3908.java,2,17
//,3
public class xxx {
public void dummy_method(){
SedaEndpoint endpoint = TestSupport .assertIsInstanceOf(SedaEndpoint.class, configuration.createEndpoint());
assertEquals("concurrentConsumers", 5, endpoint.getConcurrentConsumers());
assertEquals("size", 1000, endpoint.getSize());
assertEquals("endpoint uri", "foo?concurrentConsumers=5&size=1000", endpoint.getEndpointUri());
configuration.setEndpointParameter(endpoint, "concurrentConsumers", 6);
assertEquals("concurrentConsumers", 6, endpoint.getConcurrentConsumers());
try {
configuration.setEndpointParameter(endpoint, "doesNotExist", 1000);
fail("Should have got InvalidPropertyException thrown!");
} catch (InvalidPropertyException e) {


log.info("got expected exception");
}
}

};