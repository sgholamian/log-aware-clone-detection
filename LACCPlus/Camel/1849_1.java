//,temp,PulsarConsumerAcknowledgementTest.java,197,228,temp,PulsarConsumerAcknowledgementTest.java,142,168
//,3
public class xxx {
    @Test
    public void testAcknowledgeCumulativeAsync() throws Exception {
        to.expectedMessageCount(2);
        to.expectedBodiesReceived("testAcknowledgeCumulativeAsync: Hello World!",
                "testAcknowledgeCumulativeAsync: Hello World Again!");

        context.addRoutes(new RouteBuilder() {
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
        });

        producer.send("testAcknowledgeCumulativeAsync: Hello World!");
        producer.send("testAcknowledgeCumulativeAsync: Hello World Again!");

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};