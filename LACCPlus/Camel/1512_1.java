//,temp,BoxUsersManager.java,279,296,temp,BoxGroupsManager.java,199,218
//,3
public class xxx {
    public void deleteUserEmailAlias(String userId, String emailAliasId) {
        try {
            LOG.debug("Deleting email_alias({}) for user(id={})", emailAliasId, userId);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }
            if (emailAliasId == null) {
                throw new IllegalArgumentException("Parameter 'emailAliasId' can not be null");
            }

            BoxUser user = new BoxUser(boxConnection, userId);

            user.deleteEmailAlias(emailAliasId);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};