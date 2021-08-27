//,temp,TransformerRouteTest.java,173,177,temp,TransformerRouteTest.java,76,80
//,3
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        LOG.info("Asserting input -> XOrder convertion");
                        assertEquals(XOrder.class, exchange.getIn().getBody().getClass());
                        exchange.getIn().setBody("response");
                    }

};