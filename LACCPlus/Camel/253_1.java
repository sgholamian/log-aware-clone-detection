//,temp,DockerProducer.java,935,954,temp,DockerProducer.java,884,904
//,2
public class xxx {
    private KillContainerCmd executeKillContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Kill Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        KillContainerCmd killContainerCmd = client.killContainerCmd(containerId);

        String signal = DockerHelper.getProperty(DockerConstants.DOCKER_SIGNAL, configuration, message, String.class);

        if (signal != null) {
            killContainerCmd.withSignal(signal);
        }

        return killContainerCmd;

    }

};