//,temp,BoxUsersManager.java,161,175,temp,BoxGroupsManager.java,127,141
//,2
public class xxx {
    public BoxGroup.Info getGroupInfo(String groupId) {
        try {
            LOG.debug("Getting info for group(id={})", groupId);
            if (groupId == null) {
                throw new IllegalArgumentException("Parameter 'groupId' can not be null");
            }

            BoxGroup group = new BoxGroup(boxConnection, groupId);

            return group.getInfo();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};