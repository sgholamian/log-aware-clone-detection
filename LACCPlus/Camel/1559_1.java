//,temp,BoxUsersManager.java,232,249,temp,BoxFoldersManager.java,376,399
//,3
public class xxx {
    public EmailAlias addUserEmailAlias(String userId, String email) {
        try {
            LOG.debug("Adding email alias '{}' to user(id={})", email, userId);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }
            if (email == null) {
                throw new IllegalArgumentException("Paramerer 'email' can not be null");
            }

            BoxUser user = new BoxUser(boxConnection, userId);

            return user.addEmailAlias(email);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};