//,temp,BoxFoldersManager.java,246,267,temp,BoxFoldersManager.java,214,235
//,3
public class xxx {
    public BoxFolder moveFolder(String folderId, String destinationFolderId, String newName) {
        try {
            LOG.debug("Moving folder(id={}) to destination_folder(id={}) {}",
                    folderId, destinationFolderId, newName == null ? "" : " with new name '" + newName + "'");
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            if (destinationFolderId == null) {
                throw new IllegalArgumentException("Parameter 'destinationFolderId' can not be null");
            }
            BoxFolder folderToMove = new BoxFolder(boxConnection, folderId);
            BoxFolder destinationFolder = new BoxFolder(boxConnection, destinationFolderId);
            if (newName == null) {
                return (BoxFolder) folderToMove.move(destinationFolder).getResource();
            } else {
                return (BoxFolder) folderToMove.move(destinationFolder, newName).getResource();
            }
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};