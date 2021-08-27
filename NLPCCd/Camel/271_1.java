//,temp,sample_5564.java,2,12,temp,sample_3149.java,2,12
//,3
public class xxx {
public void testTokenize() throws InterruptedException {
template.setDefaultEndpointUri("direct:tokenize");
execute(1);
resetMock(count);
StopWatch watch = new StopWatch();
execute(1);
assertMockEndpointsSatisfied();


log.info("ran tests in ms");
}

};