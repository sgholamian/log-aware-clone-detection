//,temp,sample_5216.java,2,12,temp,sample_8226.java,2,10
//,3
public class xxx {
public void testSuspendResume() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("A");
template.sendBody("seda:foo", "A");
assertMockEndpointsSatisfied();


log.info("Suspending");
}

};