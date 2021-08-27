//,temp,sample_5216.java,2,12,temp,sample_8226.java,2,10
//,3
public class xxx {
public void testBeanOgnlPerformance() throws Exception {
StopWatch watch = new StopWatch();
getMockEndpoint("mock:result").expectedMessageCount(size);
for (int i = 0; i < size; i++) {
template.sendBody("direct:start", "Hello World");
}
assertMockEndpointsSatisfied();


log.info("took millis");
}

};