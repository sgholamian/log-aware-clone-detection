//,temp,sample_7162.java,2,16,temp,sample_7174.java,2,16
//,2
public class xxx {
public void dummy_method(){
headers.put("bar", "mock:bar");
template.sendBodyAndHeaders("direct:bar", "Hello World", headers);
assertMockEndpointsSatisfied();
names = mbeanServer.queryNames(on, null);
assertEquals(services, names.size());
context.stopRoute("bar");
boolean removed = context.removeRoute("bar");
assertTrue(removed);
names = mbeanServer.queryNames(on, null);
assertEquals(services, names.size());


log.info("shutting down");
}

};