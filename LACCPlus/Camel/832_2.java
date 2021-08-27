//,temp,BoxFilesManager.java,716,736,temp,BoxFilesManager.java,684,706
//,3
public class xxx {
    public Metadata createFileMetadata(String fileId, Metadata metadata, String typeName) {
        try {
            LOG.debug("Creating metadata for file(id={})", fileId);

            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (metadata == null) {
                throw new IllegalArgumentException("Parameter 'metadata' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);

            if (typeName != null) {
                return file.createMetadata(typeName, metadata);
            } else {
                return file.createMetadata(metadata);
            }
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};