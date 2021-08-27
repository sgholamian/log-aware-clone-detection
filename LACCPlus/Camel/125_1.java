//,temp,DockerProducer.java,1213,1225,temp,DockerProducer.java,1010,1023
//,2
public class xxx {
    private RemoveNetworkCmd executeRemoveNetworkRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Network Remove Request");

        String networkId = DockerHelper.getProperty(DockerConstants.DOCKER_NETWORK, configuration, message, String.class);

        ObjectHelper.notNull(networkId, "Network ID must be specified");

        RemoveNetworkCmd removeNetworkCmd = client.removeNetworkCmd(networkId);

        return removeNetworkCmd;

    }

};