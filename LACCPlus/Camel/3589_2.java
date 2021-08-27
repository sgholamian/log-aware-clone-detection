//,temp,TransactedQueueInOutProducerTest.java,66,99,temp,TransactedQueueProducerTest.java,65,93
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .to("sjms:queue:test.queue?transacted=true")
                        .to("sjms:queue:test.queue2?transacted=true")
                        .process(
                                new Processor() {
                                    @Override
                                    public void process(Exchange exchange) throws Exception {
                                        if (exchange.getIn().getHeader("isfailed", Boolean.class)) {
                                            log.info("We failed. Should roll back.");
                                            throw new RollbackExchangeException(exchange);
                                        } else {
                                            log.info("We passed.  Should commit.");
                                        }
                                    }
                                });

                from("sjms:queue:test.queue?transacted=true")
                        .to("mock:result");

                from("sjms:queue:test.queue2?transacted=true")
                        .to("mock:result2");
            }
        };
    }

};