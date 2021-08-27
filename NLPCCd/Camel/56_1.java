//,temp,sample_1500.java,2,11,temp,sample_2103.java,2,11
//,3
public class xxx {
public void testSuspendResume() throws Exception {
assertFalse(context.isSuspended());
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("A");
template.sendBody("seda:foo", "A");
assertMockEndpointsSatisfied();


log.info("Suspending");
}

};