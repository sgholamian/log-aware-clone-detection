//,temp,sample_4598.java,2,11,temp,sample_2102.java,2,11
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