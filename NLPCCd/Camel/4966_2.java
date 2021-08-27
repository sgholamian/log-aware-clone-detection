//,temp,sample_8006.java,2,10,temp,sample_4830.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
NatsConfiguration config = getEndpoint().getNatsConfiguration();
String body = exchange.getIn().getMandatoryBody(String.class);


log.info("publishing to topic");
}

};