//,temp,sample_6622.java,2,10,temp,sample_1203.java,2,9
//,3
public class xxx {
public void testInOut() throws Exception {
getMockEndpoint("mock:log").expectedBodiesReceived("Logging: Bye World");
final String out = template.requestBody("direct:start", "Hello World", String.class);
assertEquals("Bye World", out);


log.info("got reply");
}

};