//,temp,sample_752.java,2,11,temp,sample_8266.java,2,10
//,3
public class xxx {
public void testSplitAndAggregateInOut() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedBodiesReceived(expectedBody);
Object out = template.requestBody("direct:start", "A@B@C");
assertEquals(expectedBody, out);


log.info("response to caller");
}

};