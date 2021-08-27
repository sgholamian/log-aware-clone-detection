//,temp,sample_6743.java,2,16,temp,sample_6742.java,2,16
//,2
public class xxx {
public void dummy_method(){
context.start();
MockEndpoint mockBefore = getMockEndpoint("mock:before");
mockBefore.setExpectedMessageCount(messageCount);
MockEndpoint mockSplit = getMockEndpoint("mock:split");
mockSplit.setExpectedMessageCount(messageCount);
template.sendBody("direct:in", generateStrings(messageCount));
StopWatch stopWatch = new StopWatch();
context.startRoute("batchConsumer");
assertMockEndpointsSatisfied();
long time = stopWatch.stop();


log.info("average throughput msg s");
}

};