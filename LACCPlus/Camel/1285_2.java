//,temp,PulsarConsumerReadCompactedIT.java,121,143,temp,PulsarCustomMessageReceiptIT.java,101,129
//,3
public class xxx {
    @Test
    public void testAcknowledgeWithCustomMessageReceipt() throws Exception {
        to.expectedMinimumMessageCount(2);

        when(mockPulsarMessageReceiptFactory.newInstance(any(), any(), any())).thenReturn(mockPulsarMessageReceipt);

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

        producer.send("Hello World!");

        MockEndpoint.assertIsSatisfied(10, TimeUnit.SECONDS, to);

        // The mock does not actually acknowledge using the pulsar consumer, so
        // the message will be re-consumed
        // after the ackTimeout.
        verify(mockPulsarMessageReceipt, atLeast(2)).acknowledge();
        verifyNoMoreInteractions(mockPulsarMessageReceipt);
    }

};