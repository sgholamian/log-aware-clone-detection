//,temp,sample_1358.java,2,11,temp,sample_4580.java,2,12
//,3
public class xxx {
public void testSendingCamelExchangeToEndpointResultsInValidApplicationEventAfterTheRefreshEvent() throws Exception {
MockEndpoint result = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
result.expectedMessageCount(2);
template.sendBodyAndHeader(uri, expectedBody, "cheese", 123);
result.assertIsSatisfied();
Exchange exchange = result.getReceivedExchanges().get(0);
Object body = exchange.getIn().getBody(ContextRefreshedEvent.class);


log.info("received body");
}

};