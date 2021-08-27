//,temp,BoxGroupsManager.java,106,119,temp,BoxFilesManager.java,767,779
//,2
public class xxx {
    public void deleteGroup(String groupId) {
        try {
            LOG.debug("Deleting group({})", groupId);
            if (groupId == null) {
                throw new IllegalArgumentException("Parameter 'groupId' can not be null");
            }

            BoxGroup group = new BoxGroup(boxConnection, groupId);
            group.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};