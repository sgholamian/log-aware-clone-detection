//,temp,BoxFoldersManager.java,276,292,temp,BoxFoldersManager.java,154,169
//,3
public class xxx {
    public BoxFolder createFolder(String parentFolderId, String folderName) {
        try {
            LOG.debug("Creating folder with name '{}' in parent_folder(id={})", folderName, parentFolderId);
            if (parentFolderId == null) {
                throw new IllegalArgumentException("Parameter 'parentFolderId' can not be null");
            }
            if (folderName == null) {
                throw new IllegalArgumentException("Parameter 'folderName' can not be null");
            }
            BoxFolder parentFolder = new BoxFolder(boxConnection, parentFolderId);
            return parentFolder.createFolder(folderName).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};