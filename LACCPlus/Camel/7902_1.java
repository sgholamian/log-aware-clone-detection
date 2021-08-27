//,temp,PahoMqtt5Producer.java,40,56,temp,PahoProducer.java,40,56
//,2
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        String topic = exchange.getIn().getHeader(PahoMqtt5Constants.CAMEL_PAHO_OVERRIDE_TOPIC,
                getEndpoint().getTopic(), String.class);
        int qos = exchange.getIn().getHeader(PahoMqtt5Constants.CAMEL_PAHO_MSG_QOS,
                getEndpoint().getConfiguration().getQos(), Integer.class);
        boolean retained = exchange.getIn().getHeader(PahoMqtt5Constants.CAMEL_PAHO_MSG_RETAINED,
                getEndpoint().getConfiguration().isRetained(), Boolean.class);
        byte[] payload = exchange.getIn().getBody(byte[].class);

        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);
        message.setRetained(retained);

        LOG.debug("Publishing to topic: {}, qos: {}, retrained: {}", topic, qos, retained);
        client.publish(topic, message);
    }

};