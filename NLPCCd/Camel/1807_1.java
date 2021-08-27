//,temp,sample_2528.java,2,12,temp,sample_3262.java,2,12
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