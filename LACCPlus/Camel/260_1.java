//,temp,BoxUsersManager.java,161,175,temp,BoxGroupsManager.java,127,141
//,2
public class xxx {
    public BoxUser.Info getUserInfo(String userId) {
        try {
            LOG.debug("Getting info for user(id={})", userId);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }

            BoxUser user = new BoxUser(boxConnection, userId);

            return user.getInfo();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};