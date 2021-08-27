//,temp,PulsarConsumerAcknowledgementTest.java,176,188,temp,PulsarConsumerAcknowledgementTest.java,148,162
//,3
public class xxx {
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

};