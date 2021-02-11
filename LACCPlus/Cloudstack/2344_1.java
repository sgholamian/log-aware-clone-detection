//,temp,NeutronRestApi.java,129,141,temp,BigSwitchBcfApi.java,415,430
//,3
public class xxx {
    public void executeMethod(final HttpMethodBase method) throws NeutronRestApiException {
        try {
            client.executeMethod(method);
        } catch (HttpException e) {
            s_logger.error("HttpException caught while trying to connect to the Neutron Controller", e);
            method.releaseConnection();
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        } catch (IOException e) {
            s_logger.error("IOException caught while trying to connect to the Neutron Controller", e);
            method.releaseConnection();
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        }
    }

};