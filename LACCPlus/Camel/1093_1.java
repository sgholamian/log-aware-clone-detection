//,temp,BoxCollaborationsManager.java,185,197,temp,BoxCollaborationsManager.java,140,154
//,3
public class xxx {
    public void deleteCollaboration(String collaborationId) {
        try {
            LOG.debug("Deleting collaboration(id={})", collaborationId);
            if (collaborationId == null) {
                throw new IllegalArgumentException("Parameter 'collaborationId' can not be null");
            }
            BoxCollaboration collaboration = new BoxCollaboration(boxConnection, collaborationId);
            collaboration.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};