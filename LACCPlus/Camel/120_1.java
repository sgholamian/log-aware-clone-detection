//,temp,DockerProducer.java,1094,1107,temp,DockerProducer.java,449,461
//,2
public class xxx {
    private StartContainerCmd executeStartContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Start Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        StartContainerCmd startContainerCmd = client.startContainerCmd(containerId);

        return startContainerCmd;

    }

};