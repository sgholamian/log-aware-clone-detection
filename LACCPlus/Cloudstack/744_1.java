//,temp,CloudianClient.java,266,279,temp,CloudianClient.java,232,245
//,2
public class xxx {
    public boolean addGroup(final CloudianGroup group) {
        if (group == null) {
            return false;
        }
        LOG.debug("Adding Cloudian group: " + group);
        try {
            final HttpResponse response = put("/group", group);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (final IOException e) {
            LOG.error("Failed to add Cloudian group due to:", e);
            checkResponseTimeOut(e);
        }
        return false;
    }

};