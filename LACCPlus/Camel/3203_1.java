//,temp,TransformerRouteTest.java,65,69,temp,ExecDocumentationExamplesTest.java,183,187
//,3
public class xxx {
            @Override
            public void process(Exchange exchange) throws Exception {
                LOG.info("Asserting String -> XOrderResponse convertion");
                assertEquals(XOrderResponse.class, exchange.getIn().getBody().getClass());
            }

};