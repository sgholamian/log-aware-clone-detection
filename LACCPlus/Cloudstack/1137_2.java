//,temp,BrocadeVcsApi.java,481,506,temp,BrocadeVcsApi.java,320,353
//,3
public class xxx {
    protected <T> boolean executeUpdateObject(T newObject, String uri) throws BrocadeVcsApiException {

        final boolean result = true;

        if (_host == null || _host.isEmpty() || _adminuser == null || _adminuser.isEmpty() || _adminpass == null || _adminpass.isEmpty()) {
            throw new BrocadeVcsApiException("Hostname/credentials are null or empty");
        }

        final HttpPatch pm = (HttpPatch)createMethod("patch", uri);
        pm.setHeader("Accept", "application/vnd.configuration.resource+xml");

        pm.setEntity(new StringEntity(convertToString(newObject), ContentType.APPLICATION_XML));

        final HttpResponse response = executeMethod(pm);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {

            String errorMessage;
            try {
                errorMessage = responseToErrorMessage(response);
            } catch (final IOException e) {
                s_logger.error("Failed to update object : " + e.getMessage());
                throw new BrocadeVcsApiException("Failed to update object : " + e.getMessage());
            }

            pm.releaseConnection();
            s_logger.error("Failed to update object : " + errorMessage);
            throw new BrocadeVcsApiException("Failed to update object : " + errorMessage);
        }

        pm.releaseConnection();

        return result;
    }

};