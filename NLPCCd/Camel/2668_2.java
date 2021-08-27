//,temp,sample_4683.java,2,13,temp,sample_6587.java,2,13
//,2
public class xxx {
public void testMustachePerformance() throws Exception {
int messageCount = 10000;
endSimpleMock.expectedMessageCount(messageCount);
StopWatch stopwatch = new StopWatch(true);
for (int i = 0; i < messageCount; i++) {
startSimpleProducerTemplate.sendBodyAndHeader("The Body", "someHeader", "Some Header");
}
assertMockEndpointsSatisfied();


log.info("mustache performance ms for messages");
}

};