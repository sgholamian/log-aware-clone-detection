//,temp,SpringTransformerRouteTest.java,38,49,temp,TransformerRouteTest.java,264,278
//,3
public class xxx {
                @Override
                public boolean process(Exchange exchange, AsyncCallback callback) {
                    Object input = exchange.getIn().getBody();
                    if (input instanceof XOrderResponse) {
                        LOG.info("Endpoint: XOrderResponse -> XML");
                        exchange.getIn().setBody("<XOrderResponse/>");
                    } else {
                        assertEquals("<XOrder/>", input);
                        LOG.info("Endpoint: XML -> XOrder");
                        exchange.getIn().setBody(new XOrder());

                    }
                    callback.done(true);
                    return true;
                }

};