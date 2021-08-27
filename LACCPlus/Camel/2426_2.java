//,temp,BoxUsersManager.java,80,99,temp,BoxGroupsManager.java,270,288
//,3
public class xxx {
    public BoxGroupMembership updateGroupMembershipInfo(String groupMembershipId, BoxGroupMembership.Info info) {
        try {
            LOG.debug("Updating info for groupMembership(id={})", groupMembershipId);
            if (groupMembershipId == null) {
                throw new IllegalArgumentException("Parameter 'groupMembershipId' can not be null");
            }
            if (info == null) {
                throw new IllegalArgumentException("Parameter 'info' can not be null");
            }

            BoxGroupMembership groupMembership = new BoxGroupMembership(boxConnection, groupMembershipId);

            groupMembership.updateInfo(info);
            return groupMembership;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};