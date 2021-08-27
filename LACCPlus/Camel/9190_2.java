//,temp,UnmarshalThenMarshalJSONTest.java,25,36,temp,UnmarshalThenMarshalTest.java,59,70
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").marshal().xstream(PurchaseOrder.class).process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        log.debug("marshalled: " + exchange.getIn().getBody(String.class));
                    }
                }).unmarshal().xstream(PurchaseOrder.class).to("mock:result");
            }
        };
    }

};