//,temp,sample_6385.java,2,8,temp,sample_3544.java,2,9
//,3
public class xxx {
public void testInvokingJaxWsServerWithParams() throws Exception {
Exchange exchange = sendJaxWsMessage();
org.apache.camel.Message out = exchange.getOut();
String result = out.getBody(String.class);


log.info("received output text");
}

};