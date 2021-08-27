//,temp,sample_5522.java,2,16,temp,sample_4581.java,2,15
//,3
public class xxx {
public void dummy_method(){
body2.put("baz", 789);
template.sendBody("direct:startMulti", body1);
template.sendBody("direct:startMulti", body2);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
Message in1 = list.get(0).getIn();
String text1 = in1.getBody(String.class);
assertTrue("First CSV body has wrong value", Pattern.matches("(abc,123)|(123,abc)", text1.trim()));
Message in2 = list.get(1).getIn();
String text2 = in2.getBody(String.class);


log.info("received");
}

};