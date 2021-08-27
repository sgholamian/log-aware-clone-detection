//,temp,PahoMqtt5Producer.java,71,90,temp,PahoProducer.java,71,90
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
    }

};