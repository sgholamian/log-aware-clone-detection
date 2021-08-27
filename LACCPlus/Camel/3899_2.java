//,temp,PulsarConsumerReadCompactedIT.java,145,167,temp,PulsarConsumerAcknowledgementTest.java,230,258
//,3
public class xxx {
    @Test
    public void testNegativeAcknowledge() throws Exception {
        to.expectedMessageCount(2);
        to.expectedBodiesReceived("testNegativeAcknowledge: Hello World!", "testNegativeAcknowledge: Hello World!");

        AtomicBoolean processed = new AtomicBoolean();
        context.addRoutes(new RouteBuilder() {
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
        });

        producer.newMessage().value("testNegativeAcknowledge: Hello World!").send();

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};