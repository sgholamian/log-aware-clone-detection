//,temp,MDCOnCompletionOnCompletionTest.java,48,71,temp,MDCOnCompletionTest.java,59,86
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                // enable MDC
                context.setUseMDCLogging(true);

                from("timer:foo?period=5000").routeId("route-a").setBody().constant("Hello World").onCompletion()
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                exchange.adapt(ExtendedExchange.class).addOnCompletion(new MyOnCompletion());
                            }
                        }).end().to("log:foo").to("direct:b");

                from("direct:b").routeId("route-b").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        assertEquals("route-b", MDC.get("camel.routeId"));
                        assertEquals(exchange.getExchangeId(), MDC.get("camel.exchangeId"));
                        assertEquals(exchange.getIn().getMessageId(), MDC.get("camel.messageId"));

                        MDC.put("custom.id", "1");
                        LOG.info("From processor in route-b");
                    }
                }).to("log:bar").to("mock:result");
            }

};