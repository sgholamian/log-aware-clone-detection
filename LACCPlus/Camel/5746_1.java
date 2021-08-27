//,temp,AsyncDockerProducer.java,316,340,temp,AsyncDockerProducer.java,283,311
//,3
public class xxx {
    private PushImageCmd executePushImageRequest(DockerClient client, Message message) {

        LOG.debug("Executing Docker Push Image Request");

        String name = DockerHelper.getProperty(DockerConstants.DOCKER_NAME, configuration, message, String.class);

        ObjectHelper.notNull(name, "Image name must be specified");

        PushImageCmd pushImageCmd = client.pushImageCmd(name);

        String tag = DockerHelper.getProperty(DockerConstants.DOCKER_TAG, configuration, message, String.class);

        if (tag != null) {
            pushImageCmd.withTag(tag);
        }

        AuthConfig authConfig = client.authConfig();

        if (authConfig != null) {
            pushImageCmd.withAuthConfig(authConfig);
        }

        return pushImageCmd;

    }

};