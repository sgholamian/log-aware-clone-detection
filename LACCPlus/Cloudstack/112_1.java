//,temp,CloudianClient.java,247,260,temp,CloudianClient.java,177,190
//,3
public class xxx {
    public boolean removeUser(final String userId, final String groupId) {
        if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(groupId)) {
            return false;
        }
        LOG.debug("Removing Cloudian user with user id=" + userId + " in group id=" + groupId);
        try {
            final HttpResponse response = delete(String.format("/user?userId=%s&groupId=%s", userId, groupId));
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (final IOException e) {
            LOG.error("Failed to remove Cloudian user due to:", e);
            checkResponseTimeOut(e);
        }
        return false;
    }

};