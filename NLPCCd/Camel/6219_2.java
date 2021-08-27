//,temp,sample_3913.java,2,17,temp,sample_3914.java,2,17
//,3
public class xxx {
public void dummy_method(){
configuration.setEndpointParameter(endpoint, "bar", 10);
Object bar = configuration.getEndpointParameter(endpoint, "bar");
assertEquals("endpoint.bar", 10, bar);
configuration.setEndpointParameter(endpoint, "foo", "anotherThing");
Object foo = configuration.getEndpointParameter(endpoint, "foo");
assertEquals("endpoint.foo", "anotherThing", foo);
try {
configuration.setEndpointParameter(endpoint, "doesNotExist", 1000);
fail("Should have got InvalidPropertyException thrown!");
} catch (InvalidPropertyException e) {


log.info("got expected exception");
}
}

};