//,temp,BoxUsersManager.java,210,223,temp,BoxUsersManager.java,184,201
//,3
public class xxx {
    public BoxUser updateUserInfo(String userId, BoxUser.Info info) {
        try {
            LOG.debug("Updating info for user(id={})", userId);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }
            if (info == null) {
                throw new IllegalArgumentException("Parameter 'info' can not be null");
            }

            BoxUser user = new BoxUser(boxConnection, userId);
            user.updateInfo(info);
            return user;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};