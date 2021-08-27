//,temp,sample_856.java,2,10,temp,sample_6761.java,2,10
//,3
public class xxx {
public void testStopStart() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived("A");
template.sendBody("seda:foo", "A");
assertMockEndpointsSatisfied();


log.info("Stopping");
}

};