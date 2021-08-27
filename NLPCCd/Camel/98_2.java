//,temp,sample_4335.java,2,10,temp,sample_8269.java,2,11
//,3
public class xxx {
public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
if (oldExchange == null) {
return newExchange;
}
String orders = oldExchange.getIn().getBody(String.class);
String newLine = newExchange.getIn().getBody(String.class);


log.info("aggregate old orders");
}

};