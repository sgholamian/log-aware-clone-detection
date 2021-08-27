//,temp,sample_1849.java,2,11,temp,sample_4801.java,2,12
//,3
public class xxx {
public void testFileToCxfMessageDataFormat() throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMessageCount(1);
template.sendBodyAndHeader("file:target/filetocxf", createBody(), Exchange.FILE_NAME, "payload.xml");
assertMockEndpointsSatisfied();
String out = mock.getReceivedExchanges().get(0).getIn().getBody(String.class);
assertNotNull(out);


log.info("reply payload as a string");
}

};