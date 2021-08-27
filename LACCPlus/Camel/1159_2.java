//,temp,BoxEventsManager.java,56,77,temp,BoxCollaborationsManager.java,80,101
//,3
public class xxx {
    public BoxCollaboration addFolderCollaboration(
            String folderId, BoxCollaborator collaborator,
            BoxCollaboration.Role role) {
        try {
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            if (collaborator == null) {
                throw new IllegalArgumentException("Parameter 'collaborator' can not be null");
            }
            LOG.debug("Creating  collaborations for folder(id={}) with collaborator({})", folderId, collaborator.getID());
            if (role == null) {
                throw new IllegalArgumentException("Parameter 'role' can not be null");
            }

            BoxFolder folder = new BoxFolder(boxConnection, folderId);
            return folder.collaborate(collaborator, role).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};