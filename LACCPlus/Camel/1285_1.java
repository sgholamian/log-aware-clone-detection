//,temp,PulsarConsumerReadCompactedIT.java,121,143,temp,PulsarCustomMessageReceiptIT.java,101,129
//,3
public class xxx {
    @Test
    public void testReadCompacted() throws Exception {
        to.expectedMessageCount(1);
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

        producer.newMessage().key("myKey").value("Hello World!").send();
        producer.newMessage().key("myKey").value("Hello World! Again!").send();

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);
    }

};