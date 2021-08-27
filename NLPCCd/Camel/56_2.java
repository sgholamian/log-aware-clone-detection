//,temp,sample_1500.java,2,11,temp,sample_2103.java,2,11
//,3
public class xxx {
public void testTwoRoutesRestartProducer() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived(TEST_MESSAGE);
template.sendBody("direct:input", TEST_MESSAGE);
mock.assertIsSatisfied();
resetMocks();


log.info("restarting foo route");
}

};