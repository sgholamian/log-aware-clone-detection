//,temp,SpringTransformerRouteTest.java,38,49,temp,TransformerRouteTest.java,264,278
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) {
            Object input = exchange.getIn().getBody();
            if (input instanceof XOrderResponse) {
                LOG.info("Endpoint: XOrderResponse -> XML");
                exchange.getIn().setBody("<XOrderResponse/>");
            } else {
                assertEquals("<XOrder/>", input);
                LOG.info("Endpoint: XML -> XOrder");
                exchange.getIn().setBody(new XOrder());
            }
        }

};