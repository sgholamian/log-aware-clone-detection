//,temp,sample_3152.java,2,12,temp,sample_3150.java,2,12
//,2
public class xxx {
public void testFilterExpression() throws InterruptedException {
template.setDefaultEndpointUri("direct:filter-expression");
execute(20000);
resetMock(count);
StopWatch watch = new StopWatch();
execute(count);
assertMockEndpointsSatisfied();


log.info("ran tests in ms");
}

};