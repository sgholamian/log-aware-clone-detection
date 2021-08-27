//,temp,PahoMqtt5Producer.java,71,90,temp,PahoProducer.java,71,90
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
    }

};