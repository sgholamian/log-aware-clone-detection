//,temp,sample_6602.java,2,14,temp,sample_2637.java,2,14
//,3
public class xxx {
public void testSenderXmlData() throws Exception {
MockEndpoint result = getMockEndpoint("mock:result");
result.reset();
result.expectedMessageCount(4);
template.sendBody("direct:endpoint", xmlData);
assertMockEndpointsSatisfied();
for (Exchange exchange : result.getExchanges()) {
String message = exchange.getIn().getBody(String.class);


log.info("the message is");
}
}

};