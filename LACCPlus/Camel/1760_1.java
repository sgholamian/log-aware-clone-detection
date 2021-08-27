//,temp,AsyncEndpointRedeliveryErrorHandlerNonBlockedDelayTest.java,57,87,temp,AsyncEndpointRedeliveryErrorHandlerNonBlockedDelay2Test.java,53,88
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                context.addComponent("async", new MyAsyncComponent());

                errorHandler(defaultErrorHandler().maximumRedeliveries(5).redeliveryDelay(100).asyncDelayedRedelivery());

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
                }).to("log:after").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        beforeThreadName = Thread.currentThread().getName();
                    }
                }).to("async:bye:camel").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        afterThreadName = Thread.currentThread().getName();
                    }
                }).to("mock:result");
            }

};