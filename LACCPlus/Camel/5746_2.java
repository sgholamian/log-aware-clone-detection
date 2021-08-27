//,temp,AsyncDockerProducer.java,316,340,temp,AsyncDockerProducer.java,283,311
//,3
public class xxx {
    private PullImageCmd executePullImageRequest(DockerClient client, Message message) {

        LOG.debug("Executing Docker Pull Image Request");

        String repository = DockerHelper.getProperty(DockerConstants.DOCKER_REPOSITORY, configuration, message, String.class);

        ObjectHelper.notNull(repository, "Repository must be specified");

        PullImageCmd pullImageCmd = client.pullImageCmd(repository);

        String registry = DockerHelper.getProperty(DockerConstants.DOCKER_REGISTRY, configuration, message, String.class);
        if (registry != null) {
            pullImageCmd.withRegistry(registry);
        }

        String tag = DockerHelper.getProperty(DockerConstants.DOCKER_TAG, configuration, message, String.class);
        if (tag != null) {
            pullImageCmd.withTag(tag);
        }

        AuthConfig authConfig = client.authConfig();

        if (authConfig != null) {
            pullImageCmd.withAuthConfig(authConfig);
        }

        return pullImageCmd;

    }

};