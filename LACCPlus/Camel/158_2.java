//,temp,BoxCommentsManager.java,56,73,temp,BoxFilesManager.java,480,496
//,2
public class xxx {
    public BoxFile renameFile(String fileId, String newFileName) {
        try {
            LOG.debug("Renaming file(id={}) to '{}'", fileId, newFileName);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (newFileName == null) {
                throw new IllegalArgumentException("Parameter 'newName' can not be null");
            }
            BoxFile fileToRename = new BoxFile(boxConnection, fileId);
            fileToRename.rename(newFileName);
            return fileToRename;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};