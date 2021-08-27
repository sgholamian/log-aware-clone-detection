//,temp,sample_5566.java,2,16,temp,sample_1958.java,2,16
//,3
public class xxx {
public void dummy_method(){
mockEndpointB.expectedBodiesReceived(expectedBody);
invokeHttpEndpoint();
mockEndpointA.assertIsSatisfied();
mockEndpointB.assertIsSatisfied();
List<Exchange> list = mockEndpointA.getReceivedExchanges();
Exchange exchange = list.get(0);
assertNotNull("exchange", exchange);
Message in = exchange.getIn();
assertNotNull("in", in);
Map<String, Object> headers = in.getHeaders();


log.info("headers");
}

};