//,temp,PahoConsumer.java,54,110,temp,PahoMqtt5Consumer.java,56,122
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        connectionOptions = getEndpoint().createMqttConnectionOptions();

        if (client == null) {
            clientId = getEndpoint().getConfiguration().getClientId();
            if (clientId == null) {
                clientId = PahoMqtt5Endpoint.generateClientId();
            }
            stopClient = true;
            client = new MqttClient(
                    getEndpoint().getConfiguration().getBrokerUrl(),
                    clientId,
                    PahoMqtt5Endpoint.createMqttClientPersistence(getEndpoint().getConfiguration()));
            LOG.debug("Connecting client: {} to broker: {}", clientId, getEndpoint().getConfiguration().getBrokerUrl());
            client.connect(connectionOptions);
        }

        client.setCallback(new MqttCallback() {

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
            public void authPacketArrived(int reasonCode, MqttProperties properties) {
                LOG.debug("Auth packet arrived {} {}", reasonCode, properties);
            }

            @Override
            public void disconnected(MqttDisconnectResponse response) {
                LOG.debug("MQTT broker disconnected due {}", response.getReasonString(), response.getException());
            }

            @Override
            public void mqttErrorOccurred(MqttException exception) {
                LOG.debug("Error occurred {}", exception.getMessage(), exception);
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
            public void deliveryComplete(IMqttToken token) {
                LOG.debug("Delivery complete. Token: {}", token);
            }
        });

        LOG.debug("Subscribing client: {} to topic: {}", clientId, getEndpoint().getTopic());
        client.subscribe(getEndpoint().getTopic(), getEndpoint().getConfiguration().getQos());
    }

};