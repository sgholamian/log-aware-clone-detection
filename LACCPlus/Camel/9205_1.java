//,temp,AsyncDockerProducer.java,394,444,temp,AsyncDockerProducer.java,345,389
//,3
public class xxx {
    private LogContainerCmd executeLogContainerRequest(DockerClient client, Message message) {

        LOG.debug("Executing Docker Log Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        LogContainerCmd logContainerCmd = client.logContainerCmd(containerId);

        Boolean followStream
                = DockerHelper.getProperty(DockerConstants.DOCKER_FOLLOW_STREAM, configuration, message, Boolean.class);

        if (followStream != null) {
            logContainerCmd.withFollowStream(followStream);
        }

        Boolean stdErr = DockerHelper.getProperty(DockerConstants.DOCKER_STD_ERR, configuration, message, Boolean.class);

        if (stdErr != null) {
            logContainerCmd.withStdErr(stdErr);
        }

        Boolean stdOut = DockerHelper.getProperty(DockerConstants.DOCKER_STD_OUT, configuration, message, Boolean.class);

        if (stdOut != null) {
            logContainerCmd.withStdOut(stdOut);
        }

        Integer tail = DockerHelper.getProperty(DockerConstants.DOCKER_TAIL, configuration, message, Integer.class);

        if (tail != null) {
            logContainerCmd.withTail(tail);
        }

        Boolean tailAll = DockerHelper.getProperty(DockerConstants.DOCKER_TAIL_ALL, configuration, message, Boolean.class);

        if (tailAll != null && tailAll) {
            logContainerCmd.withTailAll();
        }

        Boolean timestamps = DockerHelper.getProperty(DockerConstants.DOCKER_TIMESTAMPS, configuration, message, Boolean.class);

        if (timestamps != null) {
            logContainerCmd.withTimestamps(timestamps);
        }

        return logContainerCmd;

    }

};