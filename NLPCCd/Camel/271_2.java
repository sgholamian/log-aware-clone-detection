//,temp,sample_5564.java,2,12,temp,sample_3149.java,2,12
//,3
public class xxx {
public void testChoiceSimple() throws InterruptedException {
template.setDefaultEndpointUri("direct:choice-simple");
execute(20000);
resetMock(count);
StopWatch watch = new StopWatch();
execute(count);
assertMockEndpointsSatisfied();


log.info("ran tests in ms");
}

};