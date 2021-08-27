//,temp,MDCOnCompletionOnCompletionTest.java,77,86,temp,MDCOnCompletionTest.java,65,73
//,3
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        assertEquals("route-a", MDC.get("camel.routeId"));
                        assertEquals(exchange.getExchangeId(), MDC.get("camel.exchangeId"));
                        assertEquals(exchange.getIn().getMessageId(), MDC.get("camel.messageId"));

                        assertEquals("1", MDC.get("custom.id"));

                        LOG.info("From onCompletion after route-a");
                    }

};