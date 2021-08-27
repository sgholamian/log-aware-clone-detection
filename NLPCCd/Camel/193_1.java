//,temp,sample_8270.java,2,11,temp,sample_1711.java,2,16
//,3
public class xxx {
public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
if (oldExchange == null) {
return newExchange;
}
String orders = oldExchange.getIn().getBody(String.class);
String newLine = newExchange.getIn().getBody(String.class);


log.info("aggregate new order");
}

};