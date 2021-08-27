//,temp,sample_1706.java,2,16,temp,sample_713.java,2,16
//,3
public class xxx {
public void dummy_method(){
MockEndpoint mockEndpoint = getMockEndpoint("mock:a");
mockEndpoint.expectedBodiesReceived(expectedBody);
invokeHttpEndpoint();
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