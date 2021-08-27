//,temp,BoxGroupsManager.java,150,167,temp,BoxFilesManager.java,96,112
//,2
public class xxx {
    public BoxGroup updateGroupInfo(String groupId, BoxGroup.Info groupInfo) {
        try {
            LOG.debug("Updating info for group(id={})", groupId);
            if (groupId == null) {
                throw new IllegalArgumentException("Parameter 'groupId' can not be null");
            }
            if (groupInfo == null) {
                throw new IllegalArgumentException("Parameter 'groupInfo' can not be null");
            }

            BoxGroup group = new BoxGroup(boxConnection, groupId);
            group.updateInfo(groupInfo);
            return group;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};