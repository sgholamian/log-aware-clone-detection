//,temp,GooglePubsubConsumer.java,143,182,temp,CamelMessageReceiver.java,50,75
//,3
public class xxx {
    @Override
    public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
        if (localLog.isTraceEnabled()) {
            localLog.trace("Received message ID : {}", pubsubMessage.getMessageId());
        }

        Exchange exchange = consumer.createExchange(true);
        exchange.getIn().setBody(pubsubMessage.getData().toByteArray());

        exchange.getIn().setHeader(GooglePubsubConstants.MESSAGE_ID, pubsubMessage.getMessageId());
        exchange.getIn().setHeader(GooglePubsubConstants.PUBLISH_TIME, pubsubMessage.getPublishTime());

        if (null != pubsubMessage.getAttributesMap()) {
            exchange.getIn().setHeader(GooglePubsubConstants.ATTRIBUTES, pubsubMessage.getAttributesMap());
        }

        if (endpoint.getAckMode() != GooglePubsubConstants.AckMode.NONE) {
            exchange.adapt(ExtendedExchange.class).addOnCompletion(new AcknowledgeAsync(ackReplyConsumer));
        }

        try {
            processor.process(exchange);
        } catch (Exception e) {
            consumer.getExceptionHandler().handleException(e);
        }
    }

};