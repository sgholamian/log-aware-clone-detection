//,temp,Sqs2Producer.java,95,111,temp,Sns2Producer.java,56,75
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        PublishRequest.Builder request = PublishRequest.builder();

        request.topicArn(getConfiguration().getTopicArn());
        request.subject(determineSubject(exchange));
        request.messageStructure(determineMessageStructure(exchange));
        request.message(exchange.getIn().getBody(String.class));
        request.messageAttributes(this.translateAttributes(exchange.getIn().getHeaders(), exchange));
        configureFifoAttributes(request, exchange);

        LOG.trace("Sending request [{}] from exchange [{}]...", request, exchange);

        PublishResponse result = getEndpoint().getSNSClient().publish(request.build());

        LOG.trace("Received result [{}]", result);

        Message message = getMessageForResponse(exchange);
        message.setHeader(Sns2Constants.MESSAGE_ID, result.messageId());
    }

};