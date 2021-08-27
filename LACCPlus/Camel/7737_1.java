//,temp,BoxUsersManager.java,58,67,temp,BoxFoldersManager.java,59,67
//,2
public class xxx {
    public BoxUser getCurrentUser() {
        try {
            LOG.debug("Getting current user");

            return BoxUser.getCurrentUser(boxConnection);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};