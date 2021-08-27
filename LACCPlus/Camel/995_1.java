//,temp,BoxFoldersManager.java,299,311,temp,BoxTasksManager.java,110,122
//,3
public class xxx {
    public void deleteFolder(String folderId) {
        try {
            LOG.debug("Deleting folder(id={})", folderId);
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            BoxFolder folder = new BoxFolder(boxConnection, folderId);
            folder.delete(true);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};