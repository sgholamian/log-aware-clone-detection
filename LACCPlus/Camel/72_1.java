//,temp,TransformerRouteTest.java,142,146,temp,TransformerRouteTest.java,120,124
//,2
public class xxx {
            @Override
            public void process(Exchange exchange) throws Exception {
                LOG.info("Asserting String -> XOrderResponse convertion is not yet performed");
                assertEquals("response", exchange.getIn().getBody());
            }

};