//,temp,sample_1410.java,2,16,temp,sample_712.java,2,16
//,3
public class xxx {
public void dummy_method(){
template.sendBody("direct:start", null);
mockEndpoint.assertIsSatisfied();
List<Exchange> list = mockEndpoint.getReceivedExchanges();
Exchange exchange = list.get(0);
assertNotNull("exchange", exchange);
Message in = exchange.getIn();
assertNotNull("in", in);
Map<String, Object> headers = in.getHeaders();
checkHeaders(headers);
String body = in.getBody(String.class);


log.info("body");
}

};