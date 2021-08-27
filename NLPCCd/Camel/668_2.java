//,temp,sample_1550.java,2,9,temp,sample_5921.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String name = exchange.getIn().getBody(String.class);
exchange.getOut().setBody("Hello " + name);


log.info("leaving processor");
}

};