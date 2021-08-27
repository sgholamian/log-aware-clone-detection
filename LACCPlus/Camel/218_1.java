//,temp,TransformerRouteTest.java,137,157,temp,TransformerRouteTest.java,115,135
//,2
public class xxx {
    @Test
    public void testCustomTransformer() throws Exception {
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
        exchange.getIn().setBody("name=XOrder");
        Exchange answerEx = template.send("direct:custom", exchange);
        if (answerEx.getException() != null) {
            throw answerEx.getException();
        }
        assertEquals("name=XOrderResponse", answerEx.getMessage().getBody(String.class));
        assertMockEndpointsSatisfied();
    }

};