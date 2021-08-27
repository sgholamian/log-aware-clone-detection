//,temp,BoxUsersManager.java,109,128,temp,BoxFilesManager.java,449,471
//,3
public class xxx {
    public BoxFile moveFile(String fileId, String destinationFolderId, String newName) {
        try {
            LOG.debug("Moving file(id={}) to destination_folder(id={}) {}",
                    fileId, destinationFolderId,
                    newName == null ? "" : " with new name '" + newName + "'");
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (destinationFolderId == null) {
                throw new IllegalArgumentException("Parameter 'destinationFolderId' can not be null");
            }
            BoxFile fileToMove = new BoxFile(boxConnection, fileId);
            BoxFolder destinationFolder = new BoxFolder(boxConnection, destinationFolderId);
            if (newName == null) {
                return (BoxFile) fileToMove.move(destinationFolder).getResource();
            } else {
                return (BoxFile) fileToMove.move(destinationFolder, newName).getResource();
            }
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};