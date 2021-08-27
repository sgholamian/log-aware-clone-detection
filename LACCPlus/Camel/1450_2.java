//,temp,BoxGroupsManager.java,247,261,temp,BoxGroupsManager.java,175,189
//,3
public class xxx {
    public Collection<BoxGroupMembership.Info> getGroupMemberships(String groupId) {
        try {
            LOG.debug("Getting information about all memberships for group(id={})", groupId);
            if (groupId == null) {
                throw new IllegalArgumentException("Parameter 'groupId' can not be null");
            }

            BoxGroup group = new BoxGroup(boxConnection, groupId);

            return group.getMemberships();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};