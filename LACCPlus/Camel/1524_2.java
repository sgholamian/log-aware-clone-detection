//,temp,BoxUsersManager.java,304,321,temp,BoxCollaborationsManager.java,113,132
//,3
public class xxx {
    public BoxCollaboration addFolderCollaborationByEmail(String folderId, String email, BoxCollaboration.Role role) {
        try {
            LOG.debug("Creating  collaborations for folder(id={}) with collaborator({})", folderId, email);
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            if (email == null) {
                throw new IllegalArgumentException("Parameter 'email' can not be null");
            }
            if (role == null) {
                throw new IllegalArgumentException("Parameter 'role' can not be null");
            }

            BoxFolder folder = new BoxFolder(boxConnection, folderId);
            return folder.collaborate(email, role).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};