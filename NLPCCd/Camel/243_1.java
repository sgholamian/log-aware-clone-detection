//,temp,sample_6622.java,2,10,temp,sample_1203.java,2,9
//,3
public class xxx {
public void testSuspendResume() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("A");
template.sendBody("direct:foo", "A");
assertMockEndpointsSatisfied();


log.info("Suspending");
}

};