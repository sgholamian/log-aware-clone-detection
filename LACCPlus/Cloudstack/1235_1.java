//,temp,Action.java,188,222,temp,Action.java,114,151
//,3
public class xxx {
    protected String executePut(final String uri) throws NeutronRestApiException {
        try {
            validateCredentials();
        } catch (NeutronInvalidCredentialsException e) {
            throw new NeutronRestApiException("Invalid credentials!", e);
        }

        NeutronRestFactory factory = NeutronRestFactory.getInstance();

        NeutronRestApi neutronRestApi = factory.getNeutronApi(PutMethod.class);
        PutMethod putMethod = (PutMethod) neutronRestApi.createMethod(url, uri);

        try {
            String encodedCredentials = encodeCredentials();
            putMethod.setRequestHeader("Authorization", "Basic " + encodedCredentials);

            neutronRestApi.executeMethod(putMethod);

            if (putMethod.getStatusCode() != HttpStatus.SC_OK) {
                String errorMessage = responseToErrorMessage(putMethod);
                putMethod.releaseConnection();
                s_logger.error("Failed to update object : " + errorMessage);
                throw new NeutronRestApiException("Failed to update object : " + errorMessage);
            }

            return putMethod.getResponseBodyAsString();
        } catch (NeutronRestApiException e) {
            s_logger.error("NeutronRestApiException caught while trying to execute HTTP Method on the Neutron Controller", e);
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        } catch (IOException e) {
            throw new NeutronRestApiException("Failed to load json response body", e);
        } finally {
            putMethod.releaseConnection();
        }
    }

};