//,temp,DockerProducer.java,1170,1183,temp,DockerProducer.java,913,926
//,2
public class xxx {
    private UnpauseContainerCmd executeUnpauseContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Unpause Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        UnpauseContainerCmd unpauseContainerCmd = client.unpauseContainerCmd(containerId);

        return unpauseContainerCmd;

    }

};