//,temp,MDCOnCompletionOnCompletionTest.java,45,73,temp,MDCOnCompletionTest.java,56,88
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // enable MDC
                context.setUseMDCLogging(true);

                from("direct:a").routeId("route-a").onCompletion().process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        assertEquals("route-a", MDC.get("camel.routeId"));
                        assertEquals(exchange.getExchangeId(), MDC.get("camel.exchangeId"));
                        assertEquals(exchange.getIn().getMessageId(), MDC.get("camel.messageId"));

                        assertEquals("1", MDC.get("custom.id"));

                        LOG.info("From onCompletion after route-a");
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
    }

};