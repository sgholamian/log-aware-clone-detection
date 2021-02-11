//,temp,BrocadeVcsApi.java,434,479,temp,BrocadeVcsApi.java,320,353
//,3
public class xxx {
    protected Output executeRetreiveStatus(String uri) throws BrocadeVcsApiException {
        if (_host == null || _host.isEmpty() || _adminuser == null || _adminuser.isEmpty() || _adminpass == null || _adminpass.isEmpty()) {
            throw new BrocadeVcsApiException("Hostname/credentials are null or empty");
        }

        String readLine = null;
        StringBuffer sb = null;

        final HttpPost pm = (HttpPost)createMethod("post", uri);
        pm.setHeader("Accept", "application/vnd.operational-state.resource+xml");
        pm.setEntity(new StringEntity("<show-vcs></show-vcs>", ContentType.APPLICATION_XML));

        final HttpResponse response = executeMethod(pm);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

            String errorMessage;
            try {
                errorMessage = responseToErrorMessage(response);
            } catch (final IOException e) {
                s_logger.error("Failed to retreive status : " + e.getMessage());
                throw new BrocadeVcsApiException("Failed to retreive status : " + e.getMessage());
            }

            pm.releaseConnection();
            s_logger.error("Failed to retreive status : " + errorMessage);
            throw new BrocadeVcsApiException("Failed to retreive status : " + errorMessage);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF-8")))) {
            sb = new StringBuffer();

            while ((readLine = br.readLine()) != null) {
                s_logger.debug(readLine);
                sb.append(readLine);

            }
        } catch (final Exception e) {
            s_logger.error("Failed to retreive status : " + e.getMessage());
            throw new BrocadeVcsApiException("Failed to retreive status : " + e.getMessage());
        }

        pm.releaseConnection();

        return convertToXML(sb.toString());
    }

};