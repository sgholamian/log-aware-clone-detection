//,temp,sample_8277.java,2,12,temp,sample_8278.java,2,13
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
PublishRequest request = new PublishRequest();
request.setTopicArn(getConfiguration().getTopicArn());
request.setSubject(determineSubject(exchange));
request.setMessageStructure(determineMessageStructure(exchange));
request.setMessage(exchange.getIn().getBody(String.class));
request.setMessageAttributes(this.translateAttributes(exchange.getIn().getHeaders(), exchange));


log.info("sending request from exchange");
}

};