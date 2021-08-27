//,temp,sample_8032.java,2,13,temp,sample_5520.java,2,17
//,3
public class xxx {
public void dummy_method(){
resultEndpoint.expectedMessageCount(1);
Map<String, Object> body = new LinkedHashMap<String, Object>();
body.put("foo", "abc");
body.put("bar", 123);
template.sendBody("direct:start", body);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : list) {
Message in = exchange.getIn();
String text = in.getBody(String.class);


log.info("received");
}
}

};