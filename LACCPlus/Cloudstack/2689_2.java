//,temp,BrocadeVcsApi.java,434,479,temp,BrocadeVcsApi.java,402,432
//,3
public class xxx {
    protected <T> boolean executeCreateObject(T newObject, String uri) throws BrocadeVcsApiException {
        if (_host == null || _host.isEmpty() || _adminuser == null || _adminuser.isEmpty() || _adminpass == null || _adminpass.isEmpty()) {
            throw new BrocadeVcsApiException("Hostname/credentials are null or empty");
        }

        final boolean result = true;
        final HttpPost pm = (HttpPost)createMethod("post", uri);
        pm.setHeader("Accept", "application/vnd.configuration.resource+xml");
        pm.setEntity(new StringEntity(convertToString(newObject), ContentType.APPLICATION_XML));

        final HttpResponse response = executeMethod(pm);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {

            String errorMessage;
            try {
                errorMessage = responseToErrorMessage(response);
            } catch (final IOException e) {
                s_logger.error("Failed to create object : " + e.getMessage());
                throw new BrocadeVcsApiException("Failed to create object : " + e.getMessage());
            }

            pm.releaseConnection();
            s_logger.error("Failed to create object : " + errorMessage);
            throw new BrocadeVcsApiException("Failed to create object : " + errorMessage);
        }

        pm.releaseConnection();

        return result;
    }

};