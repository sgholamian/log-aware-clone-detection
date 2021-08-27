//,temp,SpringIntegrationProducer.java,90,114,temp,CamelSourceAdapter.java,63,86
//,3
public class xxx {
    @Override
    public void process(final Exchange exchange) throws Exception {
        if (exchange.getPattern().isOutCapable()) {

            // we want to do in-out so the inputChannel is mandatory (used to receive reply from spring integration)
            if (inputChannel == null) {
                throw new IllegalArgumentException("InputChannel has not been configured on " + getEndpoint());
            }
            exchange.getIn().getHeaders().put(MessageHeaders.REPLY_CHANNEL, inputChannel);

            // subscribe so we can receive the reply from spring integration
            inputChannel.subscribe(new MessageHandler() {
                public void handleMessage(Message<?> message) {
                    LOG.debug("Received {} from InputChannel: {}", message, inputChannel);
                    SpringIntegrationBinding.storeToCamelMessage(message, exchange.getOut());
                }
            });
        }
        org.springframework.messaging.Message<?> siOutmessage
                = SpringIntegrationBinding.createSpringIntegrationMessage(exchange);

        // send the message to spring integration
        LOG.debug("Sending {} to OutputChannel: {}", siOutmessage, outputChannel);
        outputChannel.send(siOutmessage);
    }

};