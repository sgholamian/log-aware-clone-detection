//,temp,sample_5521.java,2,16,temp,sample_1498.java,2,16
//,3
public class xxx {
public void dummy_method(){
Map<String, Object> body2 = new LinkedHashMap<String, Object>();
body2.put("foo", "def");
body2.put("bar", 456);
body2.put("baz", 789);
template.sendBody("direct:startMulti", body1);
template.sendBody("direct:startMulti", body2);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
Message in1 = list.get(0).getIn();
String text1 = in1.getBody(String.class);


log.info("received");
}

};