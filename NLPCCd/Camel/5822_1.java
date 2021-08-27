//,temp,sample_3910.java,2,17,temp,sample_3908.java,2,17
//,3
public class xxx {
public void dummy_method(){
configuration.setParameter("bar", 5);
NonUriEndpoint endpoint = TestSupport .assertIsInstanceOf(NonUriEndpoint.class, configuration.createEndpoint());
assertEquals("foo", "xyz", endpoint.getFoo());
assertEquals("bar", 5, endpoint.getBar());
configuration.setEndpointParameter(endpoint, "bar", 6);
assertEquals("bar", 6, endpoint.getBar());
try {
configuration.setEndpointParameter(endpoint, "doesNotExist", 1000);
fail("Should have got InvalidPropertyException thrown!");
} catch (InvalidPropertyException e) {


log.info("got expected exception");
}
}

};