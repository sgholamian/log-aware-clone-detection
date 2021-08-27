//,temp,sample_7161.java,2,16,temp,sample_7169.java,2,16
//,2
public class xxx {
public void dummy_method(){
getMockEndpoint("mock:bar").expectedMessageCount(1);
getMockEndpoint("mock:done").expectedMessageCount(1);
Map<String, Object> headers = new HashMap<String, Object>();
headers.put("done", "mock:done");
headers.put("bar", "mock:bar");
template.sendBodyAndHeaders("direct:bar", "Hello World", headers);
assertMockEndpointsSatisfied();
names = mbeanServer.queryNames(on, null);
assertEquals(services, names.size());
context.stopRoute("bar");


log.info("removing route");
}

};