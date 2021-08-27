//,temp,PulsarConsumerAcknowledgementTest.java,176,188,temp,PulsarConsumerAcknowledgementTest.java,148,162
//,3
public class xxx {
            @Override
            public void configure() {
                from(from).routeId("testAcknowledgeAsync:myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    PulsarMessageReceipt receipt
                            = (PulsarMessageReceipt) exchange.getIn().getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                    try {
                        CompletableFuture<Void> f = receipt.acknowledgeAsync();
                        f.get();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                });
            }

};