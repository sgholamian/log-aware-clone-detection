//,temp,BoxUsersManager.java,279,296,temp,BoxGroupsManager.java,199,218
//,3
public class xxx {
    public BoxGroupMembership addGroupMembership(String groupId, String userId, BoxGroupMembership.Role role) {
        try {
            LOG.debug("Adding user(id={}) as member to group(id={} {})",
                    userId, groupId, role == null ? "" : "with role=" + role.name());
            if (groupId == null) {
                throw new IllegalArgumentException("Parameter 'groupId' can not be null");
            }
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }

            BoxGroup group = new BoxGroup(boxConnection, groupId);
            BoxUser user = new BoxUser(boxConnection, userId);

            return group.addMembership(user, role).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};