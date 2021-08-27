//,temp,BoxUsersManager.java,210,223,temp,BoxUsersManager.java,184,201
//,3
public class xxx {
    public void deleteUser(String userId, boolean notifyUser, boolean force) {
        try {
            LOG.debug("Deleting user(id={}) notifyUser={} force={}", userId, notifyUser, force);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }

            BoxUser file = new BoxUser(boxConnection, userId);
            file.delete(notifyUser, force);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};