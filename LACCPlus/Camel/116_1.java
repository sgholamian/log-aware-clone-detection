//,temp,DockerProducer.java,366,378,temp,AsyncDockerProducer.java,449,462
//,2
public class xxx {
    private InspectImageCmd executeInspectImageRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Inspect Image Request");

        String imageId = DockerHelper.getProperty(DockerConstants.DOCKER_IMAGE_ID, configuration, message, String.class);

        ObjectHelper.notNull(imageId, "Image ID must be specified");

        InspectImageCmd inspectImageCmd = client.inspectImageCmd(imageId);

        return inspectImageCmd;

    }

};