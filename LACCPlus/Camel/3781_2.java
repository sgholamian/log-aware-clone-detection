//,temp,SpringIntegrationProducer.java,90,114,temp,CamelSourceAdapter.java,63,86
//,3
public class xxx {
        @Override
        public void process(final Exchange exchange) throws Exception {
            org.springframework.messaging.Message<?> request
                    = SpringIntegrationBinding.createSpringIntegrationMessage(exchange);

            if (exchange.getPattern().isOutCapable()) {
                exchange.getIn().getHeaders().put(MessageHeaders.REPLY_CHANNEL, replyChannel);

                // we want to do in-out so the inputChannel is mandatory (used to receive reply from spring integration)
                if (replyChannel == null) {
                    throw new IllegalArgumentException("ReplyChannel has not been configured on: " + this);
                }

                replyChannel.subscribe(new MessageHandler() {
                    public void handleMessage(Message<?> message) {
                        LOG.debug("Received {} from ReplyChannel: {}", message, replyChannel);
                        //TODO set the correlationID
                        SpringIntegrationBinding.storeToCamelMessage(message, exchange.getOut());
                    }
                });
            }

            requestChannel.send(request);
        }

};