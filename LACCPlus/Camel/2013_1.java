//,temp,TransformerRouteTest.java,93,113,temp,TransformerRouteTest.java,60,91
//,3
public class xxx {
    @Test
    public void testDataFormatTransformer() throws Exception {
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
        ((DataTypeAware) exchange.getIn()).setBody("{name:XOrder}", new DataType("json:JsonXOrder"));
        Exchange answerEx = template.send("direct:dataFormat", exchange);
        if (answerEx.getException() != null) {
            throw answerEx.getException();
        }
        assertEquals("{name:XOrderResponse}", answerEx.getMessage().getBody(String.class));
        assertMockEndpointsSatisfied();
    }

};