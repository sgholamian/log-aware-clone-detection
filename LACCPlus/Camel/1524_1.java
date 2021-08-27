//,temp,BoxUsersManager.java,304,321,temp,BoxCollaborationsManager.java,113,132
//,3
public class xxx {
    public BoxFolder.Info moveFolderToUser(String userId, String sourceUserId) {
        try {
            LOG.debug("Moving root folder for user(id={}) to user(id={})", sourceUserId, userId);
            if (userId == null) {
                throw new IllegalArgumentException("Parameter 'userId' can not be null");
            }
            if (sourceUserId == null) {
                throw new IllegalArgumentException("Parameter 'sourceUserId' can not be null");
            }

            BoxUser user = new BoxUser(boxConnection, sourceUserId);

            return user.transferContent(userId);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};