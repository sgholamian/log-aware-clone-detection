//,temp,sample_6811.java,2,9,temp,sample_855.java,2,9
//,3
public class xxx {
public void testThreeMessagesMDC() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("Bye Camel", "Bye Camel", "Bye Camel");
template.sendBody("direct:a", "Hello World");


log.info("message");
}

};