//,temp,PahoConsumer.java,112,129,temp,PahoMqtt5Consumer.java,124,141
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        if (stopClient && client != null && client.isConnected()) {
            String topic = getEndpoint().getTopic();
            // only unsubscribe if we are not durable
            if (getEndpoint().getConfiguration().isCleanSession()) {
                LOG.debug("Unsubscribing client: {} from topic: {}", clientId, topic);
                client.unsubscribe(topic);
            } else {
                LOG.debug("Client: {} is durable so will not unsubscribe from topic: {}", clientId, topic);
            }
            LOG.debug("Disconnecting client: {} from broker: {}", clientId, getEndpoint().getConfiguration().getBrokerUrl());
            client.disconnect();
        }
        client = null;
    }

};