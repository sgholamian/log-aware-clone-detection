//,temp,sample_5566.java,2,16,temp,sample_1958.java,2,16
//,3
public class xxx {
public void dummy_method(){
mockRecovery.assertIsSatisfied();
mockResult.assertIsSatisfied();
List<Exchange> list = mockRecovery.getReceivedExchanges();
Exchange exchange = list.get(0);
assertNotNull("exchange", exchange);
Message in = exchange.getIn();
assertNotNull("in", in);
Map<String, Object> headers = in.getHeaders();
assertTrue("Should be more than one header but was: " + headers, headers.size() > 0);
String body = in.getBody(String.class);


log.info("body");
}

};