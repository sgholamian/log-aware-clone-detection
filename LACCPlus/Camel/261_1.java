//,temp,BoxUsersManager.java,257,271,temp,BoxFilesManager.java,272,287
//,2
public class xxx {
    public Collection<EmailAlias> getUserEmailAlias(String userId) {
        try {
            LOG.debug("Get email aliases for user(id={})", userId);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }

            BoxUser user = new BoxUser(boxConnection, userId);

            return user.getEmailAliases();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};