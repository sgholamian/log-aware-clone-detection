//,temp,PulsarConsumerInAsynchronousIT.java,91,97,temp,KubernetesCustomResourcesConsumerTest.java,120,127
//,3
public class xxx {
                @Override
                public void process(final Exchange exchange) throws PulsarClientException {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());
                    PulsarMessageReceipt receipt = (PulsarMessageReceipt) exchange.getIn().getHeader(
                            PulsarMessageHeaders.MESSAGE_RECEIPT);
                    receipt.acknowledge();
                }

};