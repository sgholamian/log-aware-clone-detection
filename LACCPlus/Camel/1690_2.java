//,temp,SameRouteAndContextScopedErrorHandlerIssueTest.java,47,76,temp,RedeliveryErrorHandlerBlockedDelayTest.java,53,74
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                // will by default block
                errorHandler(defaultErrorHandler().maximumRedeliveries(5).redeliveryDelay(10));

                from("seda:start").to("log:before").to("mock:before").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        LOG.info("Processing at attempt {} {}", attempt, exchange);

                        String body = exchange.getIn().getBody(String.class);
                        if (body.contains("World")) {
                            if (++attempt <= 2) {
                                LOG.info("Processing failed will thrown an exception");
                                throw new IllegalArgumentException("Damn");
                            }
                        }

                        exchange.getIn().setBody("Hello " + body);
                        LOG.info("Processing at attempt {} complete {}", attempt, exchange);
                    }
                }).to("log:after").to("mock:result");
            }

};