//,temp,DockerProducer.java,1032,1058,temp,DockerProducer.java,416,440
//,2
public class xxx {
    private RemoveContainerCmd executeRemoveContainerRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Remove Container Request");

        String containerId
                = DockerHelper.getProperty(DockerConstants.DOCKER_CONTAINER_ID, configuration, message, String.class);

        ObjectHelper.notNull(containerId, "Container ID must be specified");

        RemoveContainerCmd removeContainerCmd = client.removeContainerCmd(containerId);

        Boolean force = DockerHelper.getProperty(DockerConstants.DOCKER_FORCE, configuration, message, Boolean.class);

        if (force != null) {
            removeContainerCmd.withForce(force);
        }

        Boolean removeVolumes
                = DockerHelper.getProperty(DockerConstants.DOCKER_REMOVE_VOLUMES, configuration, message, Boolean.class);

        if (removeVolumes != null) {
            removeContainerCmd.withRemoveVolumes(removeVolumes);
        }

        return removeContainerCmd;

    }

};