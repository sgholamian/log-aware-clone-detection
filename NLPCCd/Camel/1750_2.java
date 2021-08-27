//,temp,sample_2527.java,2,12,temp,sample_3151.java,2,12
//,2
public class xxx {
public void testFilterSimple() throws InterruptedException {
template.setDefaultEndpointUri("direct:filter-simple");
execute(20000);
resetMock(count);
StopWatch watch = new StopWatch();
execute(count);
assertMockEndpointsSatisfied();


log.info("ran tests in ms");
}

};