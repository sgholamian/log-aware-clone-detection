//,temp,DockerProducer.java,1116,1133,temp,DockerProducer.java,1068,1085
//,3
public class xxx {
    private RestartContainerCmd executeRestartContainerRequest(DockerClient client, Message message) throws DockerException {

        LOGGER.debug("Executing Docker Restart Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        RestartContainerCmd restartContainerCmd = client.restartContainerCmd(containerId);

        Integer timeout = DockerHelper.getProperty(DockerConstants.DOCKER_TIMEOUT, configuration, message, Integer.class);

        if (timeout != null) {
            restartContainerCmd.withtTimeout(timeout);
        }

        return restartContainerCmd;

    }

};