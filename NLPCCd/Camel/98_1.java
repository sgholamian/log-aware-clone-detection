//,temp,sample_4335.java,2,10,temp,sample_8269.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String message = exchange.getIn().getBody(String.class);
if (null != message) {
String msh = message.substring(0, message.indexOf('\r'));


log.info("processing msh");
}
}

};