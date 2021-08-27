//,temp,PulsarConsumerAcknowledgementTest.java,197,228,temp,PulsarConsumerAcknowledgementTest.java,142,168
//,3
public class xxx {
    @Test
    public void testAcknowledgeAsync() throws Exception {
        to.expectedMessageCount(1);
        to.expectedBodiesReceived("testAcknowledgeAsync: Hello World!");

        context.addRoutes(new RouteBuilder() {
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
        });

        producer.send("testAcknowledgeAsync: Hello World!");

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};