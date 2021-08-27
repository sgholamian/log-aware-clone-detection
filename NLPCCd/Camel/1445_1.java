//,temp,sample_6602.java,2,14,temp,sample_2637.java,2,14
//,3
public class xxx {
public void testConnectionResourceRouter() throws Exception {
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