//,temp,SpringIntegrationProducer.java,102,105,temp,CamelSourceAdapter.java,77,81
//,2
public class xxx {
                public void handleMessage(Message<?> message) {
                    LOG.debug("Received {} from InputChannel: {}", message, inputChannel);
                    SpringIntegrationBinding.storeToCamelMessage(message, exchange.getOut());
                }

};