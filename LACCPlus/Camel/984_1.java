//,temp,BoxFoldersManager.java,347,363,temp,BoxFoldersManager.java,320,338
//,3
public class xxx {
    public BoxFolder updateFolderInfo(String folderId, BoxFolder.Info info) {
        try {
            LOG.debug("Updating info for folder(id={})", folderId);
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            if (info == null) {
                throw new IllegalArgumentException("Parameter 'info' can not be null");
            }
            BoxFolder folder = new BoxFolder(boxConnection, folderId);
            folder.updateInfo(info);
            return folder;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};