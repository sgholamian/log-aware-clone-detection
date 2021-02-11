//,temp,CloudianClient.java,266,279,temp,CloudianClient.java,232,245
//,2
public class xxx {
    public boolean updateUser(final CloudianUser user) {
        if (user == null) {
            return false;
        }
        LOG.debug("Updating Cloudian user: " + user);
        try {
            final HttpResponse response = post("/user", user);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (final IOException e) {
            LOG.error("Failed to update Cloudian user due to:", e);
            checkResponseTimeOut(e);
        }
        return false;
    }

};