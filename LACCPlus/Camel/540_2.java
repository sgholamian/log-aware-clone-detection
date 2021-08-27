//,temp,PulsarConsumerPatternInIT.java,130,133,temp,PulsarConcurrentConsumerInIT.java,55,71
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {

            Processor processor = new Processor() {
                @Override
                public void process(final Exchange exchange) {
                    LOGGER.info("Processing message {} on Thread {}", exchange.getIn().getBody(), Thread.currentThread());
                }
            };

            @Override
            public void configure() {
                from(from).to(to).process(processor);
            }
        };
    }

};