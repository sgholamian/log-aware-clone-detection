//,temp,BoxFilesManager.java,745,760,temp,BoxFilesManager.java,69,87
//,3
public class xxx {
    public Metadata updateFileMetadata(String fileId, Metadata metadata) {
        try {
            LOG.debug("Updating metadata for file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (metadata == null) {
                throw new IllegalArgumentException("Parameter 'metadata' can not be null");
            }
            BoxFile file = new BoxFile(boxConnection, fileId);
            return file.updateMetadata(metadata);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};