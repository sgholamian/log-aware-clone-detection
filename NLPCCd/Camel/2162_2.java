//,temp,sample_3471.java,2,14,temp,sample_5376.java,2,14
//,2
public class xxx {
public void testSynchronous() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMessageCount(100);
mock.expectsNoDuplicates(body());
StopWatch watch = new StopWatch();
for (int i = 0; i < 100; i++) {
template.sendBody("seda:start", "" + i);
}
assertMockEndpointsSatisfied(20, TimeUnit.SECONDS);


log.info("took ms to process messages request reply over jms");
}

};