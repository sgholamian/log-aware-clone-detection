//,temp,sample_6811.java,2,9,temp,sample_855.java,2,9
//,3
public class xxx {
public void testInOut() throws Exception {
getMockEndpoint("mock:log").expectedBodiesReceived("Logging: Bye World");
String out = template.requestBody("direct:start", "Hello World", String.class);
assertEquals("Bye World", out);


log.info("got reply");
}

};