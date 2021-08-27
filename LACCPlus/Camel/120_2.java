//,temp,DockerProducer.java,1094,1107,temp,DockerProducer.java,449,461
//,2
public class xxx {
    private SearchImagesCmd executeSearchImageRequest(DockerClient client, Message message) {

        LOGGER.debug("Executing Docker Search Image Request");

        String term = DockerHelper.getProperty(DockerConstants.DOCKER_TERM, configuration, message, String.class);

        ObjectHelper.notNull(term, "Term must be specified");

        SearchImagesCmd searchImagesCmd = client.searchImagesCmd(term);

        return searchImagesCmd;

    }

};