//,temp,DockerProducer.java,1116,1133,temp,DockerProducer.java,1068,1085
//,3
public class xxx {
    private StopContainerCmd executeStopContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Stop Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        StopContainerCmd stopContainerCmd = client.stopContainerCmd(containerId);

        Integer timeout = DockerHelper.getProperty(DockerConstants.DOCKER_TIMEOUT, configuration, message, Integer.class);

        if (timeout != null) {
            stopContainerCmd.withTimeout(timeout);
        }

        return stopContainerCmd;

    }

};