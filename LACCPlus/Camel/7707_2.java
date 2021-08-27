//,temp,PahoMqtt5Producer.java,92,101,temp,PahoProducer.java,92,101
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        if (stopClient && client != null && client.isConnected()) {
            LOG.debug("Disconnecting client: {} from broker: {}", clientId, getEndpoint().getConfiguration().getBrokerUrl());
            client.disconnect();
        }
        client = null;
    }

};