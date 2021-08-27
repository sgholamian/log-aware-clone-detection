//,temp,sample_355.java,2,12,temp,sample_3263.java,2,12
//,2
public class xxx {
public void testXslt() throws InterruptedException {
template.setDefaultEndpointUri("direct:xslt");
execute(1000);
resetMock(count);
StopWatch watch = new StopWatch();
execute(count);
assertMockEndpointsSatisfied();


log.info("ran tests in ms");
}

};