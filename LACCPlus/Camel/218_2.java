//,temp,TransformerRouteTest.java,137,157,temp,TransformerRouteTest.java,115,135
//,2
public class xxx {
    @Test
    public void testEndpointTransformer() throws Exception {
        MockEndpoint xyzresult = getMockEndpoint("mock:xyzresult");
        xyzresult.expectedMessageCount(1);
        xyzresult.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                LOG.info("Asserting String -> XOrderResponse convertion is not yet performed");
                assertEquals("response", exchange.getIn().getBody());
            }
        });

        Exchange exchange = new DefaultExchange(context, ExchangePattern.InOut);
        exchange.getIn().setBody("<XOrder/>");
        Exchange answerEx = template.send("direct:endpoint", exchange);
        if (answerEx.getException() != null) {
            throw answerEx.getException();
        }
        assertEquals("<XOrderResponse/>", answerEx.getMessage().getBody(String.class));
        assertMockEndpointsSatisfied();
    }

};