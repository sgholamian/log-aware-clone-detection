//,temp,DockerProducer.java,366,378,temp,AsyncDockerProducer.java,449,462
//,2
public class xxx {
    private WaitContainerCmd executeWaitContainerRequest(DockerClient client, Message message) {

        LOG.debug("Executing Docker Wait Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        WaitContainerCmd waitContainerCmd = client.waitContainerCmd(containerId);

        return waitContainerCmd;

    }

};