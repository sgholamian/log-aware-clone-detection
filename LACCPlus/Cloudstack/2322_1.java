//,temp,CloudianClient.java,333,346,temp,CloudianClient.java,318,331
//,3
public class xxx {
    public boolean removeGroup(final String groupId) {
        if (Strings.isNullOrEmpty(groupId)) {
            return false;
        }
        LOG.debug("Removing Cloudian group id=" + groupId);
        try {
            final HttpResponse response = delete(String.format("/group?groupId=%s", groupId));
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (final IOException e) {
            LOG.error("Failed to remove group due to:", e);
            checkResponseTimeOut(e);
        }
        return false;
    }

};