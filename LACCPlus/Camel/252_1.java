//,temp,BoxGroupsManager.java,225,239,temp,BoxTasksManager.java,288,300
//,2
public class xxx {
    public void deleteGroupMembership(String groupMembershipId) {
        try {
            LOG.debug("Deleting groupMembership(id={})", groupMembershipId);
            if (groupMembershipId == null) {
                throw new IllegalArgumentException("Parameter 'groupMembershipId' can not be null");
            }

            BoxGroupMembership groupMembership = new BoxGroupMembership(boxConnection, groupMembershipId);

            groupMembership.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};