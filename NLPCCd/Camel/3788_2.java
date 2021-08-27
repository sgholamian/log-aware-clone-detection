//,temp,sample_6936.java,2,16,temp,sample_6935.java,2,16
//,3
public class xxx {
public void dummy_method(){
getMockEndpoint("mock:result").expectedMessageCount(2);
String out = template.requestBody("direct:start", null, String.class);
String out2 = template.requestBody("direct:start", null, String.class);
assertEquals("9091", out);
assertEquals("9090", out2);
assertMockEndpointsSatisfied();
context.stopRoute("9090");
servers.removeServer("localhost", 9090);
String out3 = template.requestBody("direct:start", null, String.class);
assertEquals("9091", out3);


log.info("sleeping to all the server list updated to run");
}

};