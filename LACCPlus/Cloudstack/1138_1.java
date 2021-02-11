//,temp,BrocadeVcsApi.java,481,506,temp,BrocadeVcsApi.java,402,432
//,3
public class xxx {
    protected void executeDeleteObject(String uri) throws BrocadeVcsApiException {
        if (_host == null || _host.isEmpty() || _adminuser == null || _adminuser.isEmpty() || _adminpass == null || _adminpass.isEmpty()) {
            throw new BrocadeVcsApiException("Hostname/credentials are null or empty");
        }

        final HttpDelete dm = (HttpDelete)createMethod("delete", uri);
        dm.setHeader("Accept", "application/vnd.configuration.resource+xml");

        final HttpResponse response = executeMethod(dm);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {

            String errorMessage;
            try {
                errorMessage = responseToErrorMessage(response);
            } catch (final IOException e) {
                s_logger.error("Failed to delete object : " + e.getMessage());
                throw new BrocadeVcsApiException("Failed to delete object : " + e.getMessage());
            }

            dm.releaseConnection();
            s_logger.error("Failed to delete object : " + errorMessage);
            throw new BrocadeVcsApiException("Failed to delete object : " + errorMessage);
        }
        dm.releaseConnection();
    }

};