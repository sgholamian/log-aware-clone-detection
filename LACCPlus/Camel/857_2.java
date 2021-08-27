//,temp,BoxTasksManager.java,153,171,temp,BoxFilesManager.java,790,809
//,3
public class xxx {
    public void checkUpload(String fileName, String parentFolderId, Long size) {
        try {
            LOG.debug("Preflight check file with name '{}' to parent_folder(id={})", fileName, parentFolderId);
            if (parentFolderId == null) {
                throw new IllegalArgumentException("Parameter 'parentFolderId' can not be null");
            }
            if (fileName == null) {
                throw new IllegalArgumentException("Parameter 'fileName' can not be null");
            }
            if (size == null) {
                throw new IllegalArgumentException("Parameter 'size' can not be null");
            }

            BoxFolder parentFolder = new BoxFolder(boxConnection, parentFolderId);
            parentFolder.canUpload(fileName, size);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};