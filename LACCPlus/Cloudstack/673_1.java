//,temp,CloudianClient.java,318,331,temp,CloudianClient.java,232,245
//,2
public class xxx {
    public boolean updateGroup(final CloudianGroup group) {
        if (group == null) {
            return false;
        }
        LOG.debug("Updating Cloudian group: " + group);
        try {
            final HttpResponse response = post("/group", group);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (final IOException e) {
            LOG.error("Failed to update group due to:", e);
            checkResponseTimeOut(e);
        }
        return false;
    }

};