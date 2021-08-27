//,temp,PulsarConsumerAcknowledgementTest.java,170,195,temp,PulsarConsumerAcknowledgementTest.java,119,140
//,3
public class xxx {
    @Test
    public void testAcknowledge() throws Exception {
        to.expectedMessageCount(1);
        to.expectedBodiesReceived("testAcknowledge: Hello World!");

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from(from).routeId("testAcknowledge:myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    PulsarMessageReceipt receipt
                            = (PulsarMessageReceipt) exchange.getIn().getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                    receipt.acknowledge();
                });
            }
        });

        producer.send("testAcknowledge: Hello World!");

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};