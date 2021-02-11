//,temp,Action.java,224,256,temp,Action.java,114,151
//,3
public class xxx {
    protected void executeDelete(final String uri) throws NeutronRestApiException {
        try {
            validateCredentials();
        } catch (NeutronInvalidCredentialsException e) {
            throw new NeutronRestApiException("Invalid credentials!", e);
        }

        NeutronRestFactory factory = NeutronRestFactory.getInstance();

        NeutronRestApi neutronRestApi = factory.getNeutronApi(DeleteMethod.class);
        DeleteMethod deleteMethod = (DeleteMethod) neutronRestApi.createMethod(url, uri);

        try {
            deleteMethod.setRequestHeader(CONTENT_TYPE, JSON_CONTENT_TYPE);

            String encodedCredentials = encodeCredentials();
            deleteMethod.setRequestHeader("Authorization", "Basic " + encodedCredentials);

            neutronRestApi.executeMethod(deleteMethod);

            if (deleteMethod.getStatusCode() != HttpStatus.SC_NO_CONTENT) {
                String errorMessage = responseToErrorMessage(deleteMethod);
                deleteMethod.releaseConnection();
                s_logger.error("Failed to delete object : " + errorMessage);
                throw new NeutronRestApiException("Failed to delete object : " + errorMessage);
            }
        } catch (NeutronRestApiException e) {
            s_logger.error("NeutronRestApiException caught while trying to execute HTTP Method on the Neutron Controller", e);
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        } finally {
            deleteMethod.releaseConnection();
        }
    }

};