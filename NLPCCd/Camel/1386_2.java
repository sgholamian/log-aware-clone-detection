//,temp,sample_5522.java,2,16,temp,sample_4581.java,2,15
//,3
public class xxx {
public void testSendingCamelExchangeToEndpointResultsInValidApplicationEventAfterTheRefreshEvent() throws Exception {
MockEndpoint result = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
result.expectedMessageCount(2);
template.sendBodyAndHeader(uri, expectedBody, "cheese", 123);
result.assertIsSatisfied();
Exchange exchange = result.getReceivedExchanges().get(0);
Object body = exchange.getIn().getBody(ContextRefreshedEvent.class);
assertNotNull(body);
exchange = result.getReceivedExchanges().get(1);
body = exchange.getIn().getBody();


log.info("received body");
}

};