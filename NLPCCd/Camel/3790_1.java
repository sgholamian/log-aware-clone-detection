//,temp,sample_4174.java,2,16,temp,sample_5525.java,2,13
//,3
public class xxx {
public void testSenderXmlData() throws Exception {
MockEndpoint result = getMockEndpoint("mock:result");
result.reset();
result.expectedMessageCount(4);
template.sendBody("direct:endpoint", xmlData);
assertMockEndpointsSatisfied();
int i = 0;
for (Exchange exchange : result.getExchanges()) {
Element element = (Element) exchange.getIn().getBody();
String message = CxfUtils.elementToString(element);


log.info("the splited message is");
}
}

};