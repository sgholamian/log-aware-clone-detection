//,temp,PahoConsumer.java,76,85,temp,PahoMqtt5Consumer.java,78,87
//,2
public class xxx {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    try {
                        client.subscribe(getEndpoint().getTopic(), getEndpoint().getConfiguration().getQos());
                    } catch (MqttException e) {
                        LOG.error("MQTT resubscribe failed {}", e.getMessage(), e);
                    }
                }
            }

};