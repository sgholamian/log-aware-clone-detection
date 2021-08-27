//,temp,PulsarConsumerReadCompactedIT.java,145,167,temp,PulsarConsumerAcknowledgementTest.java,230,258
//,3
public class xxx {
    @Test
    public void testReadNotCompacted() throws Exception {
        to.expectedMessageCount(2);
        triggerCompaction();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from(from).routeId("myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    PulsarMessageReceipt receipt
                            = (PulsarMessageReceipt) exchange.getIn().getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                    receipt.acknowledge();
                });
            }
        });

        producer.newMessage().key("mySecondKey").value("Hello World!").send();
        producer.newMessage().key("mySecondKey").value("Hello World! Again!").send();

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};