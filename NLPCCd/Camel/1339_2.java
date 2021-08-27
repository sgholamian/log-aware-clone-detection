//,temp,sample_5693.java,2,11,temp,sample_5694.java,2,12
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String body = exchange.getIn().getBody(String.class);
SendMessageRequest request = new SendMessageRequest(getQueueUrl(), body);
request.setMessageAttributes(translateAttributes(exchange.getIn().getHeaders(), exchange));
addDelay(request, exchange);
configureFifoAttributes(request, exchange);
SendMessageResult result = getClient().sendMessage(request);


log.info("received result");
}

};