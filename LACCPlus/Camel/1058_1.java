//,temp,PulsarConsumerAcknowledgementTest.java,170,195,temp,PulsarConsumerAcknowledgementTest.java,119,140
//,3
public class xxx {
    @Test
    public void testAcknowledgeCumulative() throws Exception {
        to.expectedMessageCount(2);
        to.expectedBodiesReceived("testAcknowledgeCumulative: Hello World!", "testAcknowledgeCumulative: Hello World Again!");

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from(from).routeId("testAcknowledgeCumulative:myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    PulsarMessageReceipt receipt
                            = (PulsarMessageReceipt) exchange.getIn().getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                    // Ack the second message. The first will also be acked.
                    if (exchange.getIn().getBody().equals("testAcknowledgeCumulative: Hello World Again!")) {
                        receipt.acknowledgeCumulative();
                    }
                });
            }
        });

        producer.send("testAcknowledgeCumulative: Hello World!");
        producer.send("testAcknowledgeCumulative: Hello World Again!");

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};