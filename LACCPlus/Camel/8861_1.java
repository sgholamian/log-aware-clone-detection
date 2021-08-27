//,temp,NsqProducer.java,46,53,temp,NettyUdpWithInOutUsingPlainSocketTest.java,71,78
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        String topic = exchange.getIn().getHeader(NsqConstants.NSQ_MESSAGE_TOPIC, configuration.getTopic(), String.class);

        LOG.debug("Publishing to topic: {}", topic);
        byte[] body = exchange.getIn().getBody(byte[].class);
        producer.produce(topic, body);
    }

};