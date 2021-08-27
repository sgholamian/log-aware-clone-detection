//,temp,MDCOnCompletionOnCompletionTest.java,62,69,temp,MDCOnCompletionTest.java,77,84
//,2
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        assertEquals("route-b", MDC.get("camel.routeId"));
                        assertEquals(exchange.getExchangeId(), MDC.get("camel.exchangeId"));
                        assertEquals(exchange.getIn().getMessageId(), MDC.get("camel.messageId"));

                        MDC.put("custom.id", "1");
                        LOG.info("From processor in route-b");
                    }

};