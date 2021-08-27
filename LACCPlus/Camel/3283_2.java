//,temp,DisruptorRouteTest.java,42,52,temp,TransactedTopicProducerTest.java,70,91
//,3
public class xxx {
            @Override
            public void configure() {

                from("direct:start")
                        .to("sjms:topic:test.topic?transacted=true")
                        .process(
                                new Processor() {
                                    @Override
                                    public void process(Exchange exchange) throws Exception {
                                        if (exchange.getIn().getHeader("isfailed", Boolean.class)) {
                                            log.info("We failed.  Should roll back.");
                                            throw new RollbackExchangeException(exchange);
                                        } else {
                                            log.info("We passed.  Should commit.");
                                        }
                                    }
                                });

                from("sjms:topic:test.topic?durableSubscriptionName=bar&transacted=true")
                        .to("mock:result");

            }

};