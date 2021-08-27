//,temp,PulsarConsumerAcknowledgementTest.java,237,252,temp,PulsarConsumerAcknowledgementTest.java,204,221
//,3
public class xxx {
            @Override
            public void configure() {
                from(from).routeId("testNegativeAcknowledge:myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    if (processed.compareAndSet(false, true)) {
                        PulsarMessageReceipt receipt = (PulsarMessageReceipt) exchange.getIn()
                                .getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                        receipt.negativeAcknowledge();
                    } else {
                        PulsarMessageReceipt receipt = (PulsarMessageReceipt) exchange.getIn()
                                .getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                        receipt.acknowledge();
                    }
                });
            }

};