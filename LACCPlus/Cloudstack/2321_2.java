//,temp,CloudianClient.java,333,346,temp,CloudianClient.java,177,190
//,3
public class xxx {
    public boolean addUser(final CloudianUser user) {
        if (user == null) {
            return false;
        }
        LOG.debug("Adding Cloudian user: " + user);
        try {
            final HttpResponse response = put("/user", user);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (final IOException e) {
            LOG.error("Failed to add Cloudian user due to:", e);
            checkResponseTimeOut(e);
        }
        return false;
    }

};