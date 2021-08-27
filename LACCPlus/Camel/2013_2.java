//,temp,TransformerRouteTest.java,93,113,temp,TransformerRouteTest.java,60,91
//,3
public class xxx {
    @Test
    public void testJavaTransformer() throws Exception {
        MockEndpoint abcresult = getMockEndpoint("mock:abcresult");
        abcresult.expectedMessageCount(1);
        abcresult.whenAnyExchangeReceived(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                LOG.info("Asserting String -> XOrderResponse convertion");
                assertEquals(XOrderResponse.class, exchange.getIn().getBody().getClass());
            }

        });

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
        exchange.getIn().setBody(new AOrder());
        Exchange answerEx = template.send("direct:abc", exchange);
        if (answerEx.getException() != null) {
            throw answerEx.getException();
        }
        assertEquals(AOrderResponse.class, answerEx.getMessage().getBody().getClass());
        assertMockEndpointsSatisfied();
    }

};