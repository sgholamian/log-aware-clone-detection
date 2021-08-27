//,temp,PulsarConsumerInIT.java,54,70,temp,MinioObjectRangeOperationIT.java,78,96
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {

            Processor processor = new Processor() {
                @Override
                public void process(final Exchange exchange) {
                    LOGGER.info("Processing message {}", exchange.getIn().getBody());
                }
            };

            @Override
            public void configure() {
                from(from).to(to).process(processor);
            }
        };
    }

};