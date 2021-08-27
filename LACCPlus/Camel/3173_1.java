//,temp,PulsarCustomMessageReceiptIT.java,108,117,temp,NettyUdpWithInOutUsingPlainSocketTest.java,69,80
//,3
public class xxx {
            @Override
            public void configure() {
                from(from).routeId("myRoute").to(to).process(exchange -> {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());

                    PulsarMessageReceipt receipt
                            = (PulsarMessageReceipt) exchange.getIn().getHeader(PulsarMessageHeaders.MESSAGE_RECEIPT);
                    receipt.acknowledge();
                });
            }

};