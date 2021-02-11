//,temp,BigSwitchBcfApi.java,415,430,temp,BrocadeVcsApi.java,508,523
//,3
public class xxx {
    protected HttpResponse executeMethod(HttpRequestBase method) throws BrocadeVcsApiException {
        HttpResponse response = null;
        try {
            response = _client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                method.releaseConnection();
                response = _client.execute(method);
            }
        } catch (final IOException e) {
            s_logger.error("IOException caught while trying to connect to the Brocade Switch", e);
            method.releaseConnection();
            throw new BrocadeVcsApiException("API call to Brocade Switch Failed", e);
        }

        return response;
    }

};