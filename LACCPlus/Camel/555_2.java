//,temp,TransformerRouteTest.java,173,177,temp,TransformerRouteTest.java,76,80
//,3
public class xxx {
            @Override
            public void process(Exchange exchange) throws Exception {
                LOG.info("Asserting String -> XOrderResponse convertion is not yet performed");
                assertEquals("response", exchange.getIn().getBody());
            }

};