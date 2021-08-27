//,temp,sample_1849.java,2,11,temp,sample_4801.java,2,12
//,3
public class xxx {
public void testPollFileWhileSlowFileIsBeingWritten() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMessageCount(1);
template.sendBody("seda:start", "Create the slow file");
mock.assertIsSatisfied();
String body = mock.getReceivedExchanges().get(0).getIn().getBody(String.class);


log.info("body is");
}

};