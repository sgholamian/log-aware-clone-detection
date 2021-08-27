//,temp,BoxGroupsManager.java,247,261,temp,BoxGroupsManager.java,175,189
//,3
public class xxx {
    public BoxGroupMembership.Info getGroupMembershipInfo(String groupMembershipId) {
        try {
            LOG.debug("Getting info for groupMemebership(id={})", groupMembershipId);
            if (groupMembershipId == null) {
                throw new IllegalArgumentException("Parameter 'groupMembershipId' can not be null");
            }

            BoxGroupMembership group = new BoxGroupMembership(boxConnection, groupMembershipId);

            return group.getInfo();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};