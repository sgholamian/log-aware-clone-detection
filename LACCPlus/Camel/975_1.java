//,temp,BoxFoldersManager.java,276,292,temp,BoxFoldersManager.java,154,169
//,3
public class xxx {
    public BoxFolder renameFolder(String folderId, String newFolderName) {
        try {
            LOG.debug("Renaming folder(id={}}) to '{}'", folderId, newFolderName);
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            if (newFolderName == null) {
                throw new IllegalArgumentException("Parameter 'newFolderName' can not be null");
            }
            BoxFolder folderToRename = new BoxFolder(boxConnection, folderId);
            folderToRename.rename(newFolderName);
            return folderToRename;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};