//,temp,sample_856.java,2,10,temp,sample_6761.java,2,10
//,3
public class xxx {
public void testThreeMessagesMDC() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("Bye Camel", "Bye Camel", "Bye Camel");
template.sendBody("direct:a", "Hello World");
template.sendBody("direct:a", "Hello Camel");


log.info("message");
}

};