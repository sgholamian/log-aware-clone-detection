//,temp,sample_7609.java,2,16,temp,sample_5202.java,2,17
//,3
public class xxx {
private void listBrokers(AmazonMQ mqClient, Exchange exchange) {
ListBrokersRequest request = new ListBrokersRequest();
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQConstants.MAX_RESULTS))) {
int maxResults = exchange.getIn().getHeader(MQConstants.MAX_RESULTS, Integer.class);
request.withMaxResults(maxResults);
}
ListBrokersResult result;
try {
result = mqClient.listBrokers(request);
} catch (AmazonServiceException ase) {


log.info("list brokers command returned the error code");
}
}

};