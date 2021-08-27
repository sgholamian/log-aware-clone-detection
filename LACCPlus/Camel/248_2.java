//,temp,BoxGroupsManager.java,106,119,temp,BoxFilesManager.java,767,779
//,2
public class xxx {
    public void deleteFileMetadata(String fileId) {
        try {
            LOG.debug("Deleting metadata for file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            BoxFile file = new BoxFile(boxConnection, fileId);
            file.deleteMetadata();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};