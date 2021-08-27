//,temp,PulsarConsumerAcknowledgementTest.java,237,252,temp,PulsarConsumerAcknowledgementTest.java,204,221
//,3
public class xxx {
            @Override
            public void configure() {
                from(from).routeId("testAcknowledgeCumulativeAsync:myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    PulsarMessageReceipt receipt
                            = (PulsarMessageReceipt) exchange.getIn().getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                    // Ack the second message. The first will also be acked.
                    if (exchange.getIn().getBody().equals("testAcknowledgeCumulativeAsync: Hello World Again!")) {
                        try {
                            CompletableFuture<Void> f = receipt.acknowledgeCumulativeAsync();
                            f.get();
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                });
            }

};