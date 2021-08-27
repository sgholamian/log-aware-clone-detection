//,temp,PulsarConsumerPatternInIT.java,130,133,temp,PulsarConcurrentConsumerInIT.java,55,71
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                from(from).to(to).process(e -> LOGGER.info("Processing message {}", e.getIn().getBody(String.class)));
            }

};