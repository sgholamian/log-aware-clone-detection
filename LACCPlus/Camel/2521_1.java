//,temp,PahoConsumer.java,54,110,temp,PahoMqtt5Consumer.java,56,122
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        connectOptions = PahoEndpoint.createMqttConnectOptions(getEndpoint().getConfiguration());

        if (client == null) {
            clientId = getEndpoint().getConfiguration().getClientId();
            if (clientId == null) {
                clientId = "camel-" + MqttClient.generateClientId();
            }
            stopClient = true;
            client = new MqttClient(
                    getEndpoint().getConfiguration().getBrokerUrl(),
                    clientId,
                    PahoEndpoint.createMqttClientPersistence(getEndpoint().getConfiguration()));
            LOG.debug("Connecting client: {} to broker: {}", clientId, getEndpoint().getConfiguration().getBrokerUrl());
            client.connect(connectOptions);
        }

        client.setCallback(new MqttCallbackExtended() {

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

            @Override
            public void connectionLost(Throwable cause) {
                LOG.debug("MQTT broker connection lost due {}", cause.getMessage(), cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                LOG.debug("Message arrived on topic: {} -> {}", topic, message);
                Exchange exchange = createExchange(message, topic);

                // use default consumer callback
                AsyncCallback cb = defaultConsumerCallback(exchange, true);
                getAsyncProcessor().process(exchange, cb);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                LOG.debug("Delivery complete. Token: {}", token);
            }
        });

        LOG.debug("Subscribing client: {} to topic: {}", clientId, getEndpoint().getTopic());
        client.subscribe(getEndpoint().getTopic(), getEndpoint().getConfiguration().getQos());
    }

};