//,temp,BoxCollaborationsManager.java,57,69,temp,BoxTasksManager.java,179,194
//,2
public class xxx {
    public Collection<BoxCollaboration.Info> getFolderCollaborations(String folderId) {
        try {
            LOG.debug("Getting collaborations for folder(id={})", folderId);
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            BoxFolder folder = new BoxFolder(boxConnection, folderId);
            return folder.getCollaborations();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};