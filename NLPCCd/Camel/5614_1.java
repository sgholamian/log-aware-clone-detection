//,temp,sample_4336.java,2,10,temp,sample_2321.java,2,13
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String message = exchange.getIn().getBody(String.class);
if (null != message) {
String msh = message.substring(0, message.indexOf('\r'));
}


log.info("null inbound message body");
}

};