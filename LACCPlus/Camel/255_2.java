//,temp,DockerProducer.java,1032,1058,temp,DockerProducer.java,416,440
//,2
public class xxx {
    private RemoveImageCmd executeRemoveImageRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Remove Image Request");

        String imageId = DockerHelper.getProperty(DockerConstants.DOCKER_IMAGE_ID, configuration, message, String.class);

        ObjectHelper.notNull(imageId, "Image ID must be specified");

        RemoveImageCmd removeImagesCmd = client.removeImageCmd(imageId);

        Boolean force = DockerHelper.getProperty(DockerConstants.DOCKER_FORCE, configuration, message, Boolean.class);

        if (force != null) {
            removeImagesCmd.withForce(force);
        }

        Boolean noPrune = DockerHelper.getProperty(DockerConstants.DOCKER_NO_PRUNE, configuration, message, Boolean.class);

        if (noPrune != null) {
            removeImagesCmd.withNoPrune(noPrune);
        }

        return removeImagesCmd;

    }

};