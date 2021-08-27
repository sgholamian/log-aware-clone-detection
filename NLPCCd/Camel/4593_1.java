//,temp,sample_1750.java,2,12,temp,sample_2526.java,2,12
//,2
public class xxx {
public void testFilter() throws InterruptedException {
template.setDefaultEndpointUri("direct:filter");
execute(20000);
resetMock(count);
StopWatch watch = new StopWatch();
execute(count);
assertMockEndpointsSatisfied();


log.info("ran tests in ms");
}

};