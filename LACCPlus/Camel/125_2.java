//,temp,DockerProducer.java,1213,1225,temp,DockerProducer.java,1010,1023
//,2
public class xxx {
    private PauseContainerCmd executePauseContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Pause Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        PauseContainerCmd pauseContainerCmd = client.pauseContainerCmd(containerId);

        return pauseContainerCmd;

    }

};