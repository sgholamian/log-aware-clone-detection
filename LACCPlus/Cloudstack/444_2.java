//,temp,Action.java,188,222,temp,Action.java,153,186
//,3
public class xxx {
    protected void executePut(final String uri, final StringRequestEntity entity) throws NeutronRestApiException {
        try {
            validateCredentials();
        } catch (NeutronInvalidCredentialsException e) {
            throw new NeutronRestApiException("Invalid credentials!", e);
        }

        NeutronRestFactory factory = NeutronRestFactory.getInstance();

        NeutronRestApi neutronRestApi = factory.getNeutronApi(PutMethod.class);
        PutMethod putMethod = (PutMethod) neutronRestApi.createMethod(url, uri);

        try {
            putMethod.setRequestHeader(CONTENT_TYPE, JSON_CONTENT_TYPE);
            putMethod.setRequestEntity(entity);

            String encodedCredentials = encodeCredentials();
            putMethod.setRequestHeader("Authorization", "Basic " + encodedCredentials);

            neutronRestApi.executeMethod(putMethod);

            if (putMethod.getStatusCode() != HttpStatus.SC_OK) {
                String errorMessage = responseToErrorMessage(putMethod);
                putMethod.releaseConnection();
                s_logger.error("Failed to update object : " + errorMessage);
                throw new NeutronRestApiException("Failed to update object : " + errorMessage);
            }
        } catch (NeutronRestApiException e) {
            s_logger.error("NeutronRestApiException caught while trying to execute HTTP Method on the Neutron Controller", e);
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        } finally {
            putMethod.releaseConnection();
        }
    }

};