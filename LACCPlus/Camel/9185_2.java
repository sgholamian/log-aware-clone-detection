//,temp,UnmarshalThenMarshalJSONTest.java,28,34,temp,UnmarshalThenMarshalTest.java,62,68
//,3
public class xxx {
            public void configure() {
                from("direct:start").marshal().xstream(PurchaseOrder.class).process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        log.debug("marshalled: " + exchange.getIn().getBody(String.class));
                    }
                }).unmarshal().xstream(PurchaseOrder.class).to("mock:result");
            }

};