//,temp,sample_1409.java,2,16,temp,sample_807.java,2,16
//,3
public class xxx {
public void dummy_method(){
MockEndpoint mockEndpoint = resolveMandatoryEndpoint("mock:results", MockEndpoint.class);
mockEndpoint.expectedMessageCount(1);
template.sendBody("direct:start", null);
mockEndpoint.assertIsSatisfied();
List<Exchange> list = mockEndpoint.getReceivedExchanges();
Exchange exchange = list.get(0);
assertNotNull("exchange", exchange);
Message in = exchange.getIn();
assertNotNull("in", in);
Map<String, Object> headers = in.getHeaders();


log.info("headers");
}

};