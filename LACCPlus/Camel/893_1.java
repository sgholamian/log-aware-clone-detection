//,temp,SameRouteAndContextScopedErrorHandlerIssueTest.java,50,74,temp,RedeliveryErrorHandlerBlockedDelayTest.java,50,76
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(0));

                onException(IllegalArgumentException.class).onRedelivery(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("OnRedelivery invoked");
                        counter.incrementAndGet();
                    }
                });

                from("direct:start").errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(0))
                        .process(new Processor() {
                            private int counter;

                            @Override
                            public void process(Exchange exchange) throws Exception {
                                if (counter++ < 2) {
                                    throw new IllegalArgumentException("Damn");
                                }
                                exchange.getIn().setBody("Bye World");
                            }
                        }).to("log:result").to("mock:result");
            }

};