//,temp,AsyncDockerProducer.java,394,444,temp,AsyncDockerProducer.java,345,389
//,3
public class xxx {
    private AttachContainerCmd executeAttachContainerRequest(DockerClient client, Message message) {

        LOG.debug("Executing Docker Attach Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        AttachContainerCmd attachContainerCmd = client.attachContainerCmd(containerId);

        Boolean followStream
                = DockerHelper.getProperty(DockerConstants.DOCKER_FOLLOW_STREAM, configuration, message, Boolean.class);

        if (followStream != null) {
            attachContainerCmd.withFollowStream(followStream);
        }

        Boolean logs = DockerHelper.getProperty(DockerConstants.DOCKER_LOGS, configuration, message, Boolean.class);

        if (logs != null) {
            attachContainerCmd.withLogs(logs);
        }

        Boolean stdErr = DockerHelper.getProperty(DockerConstants.DOCKER_STD_ERR, configuration, message, Boolean.class);

        if (stdErr != null) {
            attachContainerCmd.withStdErr(stdErr);
        }

        Boolean stdOut = DockerHelper.getProperty(DockerConstants.DOCKER_STD_OUT, configuration, message, Boolean.class);

        if (stdOut != null) {
            attachContainerCmd.withStdOut(stdOut);
        }

        Boolean timestamps = DockerHelper.getProperty(DockerConstants.DOCKER_TIMESTAMPS, configuration, message, Boolean.class);

        if (timestamps != null) {
            attachContainerCmd.withTimestamps(timestamps);
        }

        return attachContainerCmd;

    }

};