//,temp,TransformerRouteTest.java,166,169,temp,S3ObjectRangeOperationManualIT.java,97,103
//,3
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        LOG.info("Asserting input -> AOrder convertion");
                        assertEquals(AOrder.class, exchange.getIn().getBody().getClass());
                    }

};