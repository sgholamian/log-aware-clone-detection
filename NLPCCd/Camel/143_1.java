//,temp,sample_854.java,2,8,temp,sample_6189.java,2,8
//,3
public class xxx {
public void testThreeMessagesMDC() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("Bye Camel", "Bye Camel", "Bye Camel");


log.info("message");
}

};