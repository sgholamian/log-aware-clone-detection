//,temp,BoxCollaborationsManager.java,163,178,temp,BoxFilesManager.java,385,405
//,3
public class xxx {
    public BoxCollaboration updateCollaborationInfo(String collaborationId, BoxCollaboration.Info info) {
        try {
            LOG.debug("Updating info for collaboration(id={})", collaborationId);
            if (collaborationId == null) {
                throw new IllegalArgumentException("Parameter 'collaborationId' can not be null");
            }

            BoxCollaboration collaboration = new BoxCollaboration(boxConnection, collaborationId);

            collaboration.updateInfo(info);
            return collaboration;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};