//,temp,GuavaEventBusProducer.java,41,50,temp,PassthroughProcessor.java,37,46
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getIn().getBody(String.class);
        if (null != message) {
            String msh = message.substring(0, message.indexOf('\r'));
            log.debug("Processing MSH {}: \n{}\n", id, msh);
        }

        log.debug("Null inbound message body");
    }

};