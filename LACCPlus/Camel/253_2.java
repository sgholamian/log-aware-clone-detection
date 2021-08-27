//,temp,DockerProducer.java,935,954,temp,DockerProducer.java,884,904
//,2
public class xxx {
    private ContainerDiffCmd executeDiffContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Diff Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        ContainerDiffCmd diffContainerCmd = client.containerDiffCmd(containerId);

        String containerIdDiff
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID_DIFF, configuration, message, String.class);

        if (containerIdDiff != null) {
            diffContainerCmd.withContainerId(containerIdDiff);
        }

        return diffContainerCmd;

    }

};