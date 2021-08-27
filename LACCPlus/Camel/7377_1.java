//,temp,Sqs2Producer.java,95,111,temp,Sns2Producer.java,56,75
//,3
public class xxx {
    public void processSingleMessage(final Exchange exchange) {
        String body = exchange.getIn().getBody(String.class);
        SendMessageRequest.Builder request = SendMessageRequest.builder().queueUrl(getQueueUrl()).messageBody(body);
        request.messageAttributes(translateAttributes(exchange.getIn().getHeaders(), exchange));
        addDelay(request, exchange);
        configureFifoAttributes(request, exchange);

        LOG.trace("Sending request [{}] from exchange [{}]...", request, exchange);

        SendMessageResponse result = getClient().sendMessage(request.build());

        LOG.trace("Received result [{}]", result);

        Message message = getMessageForResponse(exchange);
        message.setHeader(Sqs2Constants.MESSAGE_ID, result.messageId());
        message.setHeader(Sqs2Constants.MD5_OF_BODY, result.md5OfMessageBody());
    }

};