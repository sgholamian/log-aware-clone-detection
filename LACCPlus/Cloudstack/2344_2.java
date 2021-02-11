//,temp,NeutronRestApi.java,129,141,temp,BigSwitchBcfApi.java,415,430
//,3
public class xxx {
    protected void executeMethod(final HttpMethodBase method) throws BigSwitchBcfApiException {
        try {
            _client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                method.releaseConnection();
            }
        } catch (HttpException e) {
            S_LOGGER.error("HttpException caught while trying to connect to the BigSwitch Controller", e);
            method.releaseConnection();
            throw new BigSwitchBcfApiException("API call to BigSwitch Controller Failed", e);
        } catch (IOException e) {
            S_LOGGER.error("IOException caught while trying to connect to the BigSwitch Controller", e);
            method.releaseConnection();
            throw new BigSwitchBcfApiException("API call to BigSwitch Controller Failed", e);
        }
    }

};