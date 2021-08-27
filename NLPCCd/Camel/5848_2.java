//,temp,sample_5171.java,2,13,temp,sample_5167.java,2,13
//,3
public class xxx {
public void testSendMessagesThenBrowseQueue() throws Exception {
for (int i = 0; i < expectedBodies.length; i++) {
Object expectedBody = expectedBodies[i];
template.sendBodyAndHeader("activemq:test.b", expectedBody, "counter", i);
}
JmsQueueEndpoint endpoint = getMandatoryEndpoint("activemq:test.b?maximumBrowseSize=6", JmsQueueEndpoint.class);
assertEquals(6, endpoint.getMaximumBrowseSize());
List<Exchange> list = endpoint.getExchanges();


log.info("received");
}

};