//,temp,Action.java,114,151,temp,Action.java,67,112
//,3
public class xxx {
    protected String executePost(final String uri, final StringRequestEntity entity) throws NeutronRestApiException {
        try {
            validateCredentials();
        } catch (NeutronInvalidCredentialsException e) {
            throw new NeutronRestApiException("Invalid credentials!", e);
        }

        NeutronRestFactory factory = NeutronRestFactory.getInstance();

        NeutronRestApi neutronRestApi = factory.getNeutronApi(PostMethod.class);
        PostMethod postMethod = (PostMethod) neutronRestApi.createMethod(url, uri);

        try {
            postMethod.setRequestHeader(CONTENT_TYPE, JSON_CONTENT_TYPE);
            postMethod.setRequestEntity(entity);

            String encodedCredentials = encodeCredentials();
            postMethod.setRequestHeader("Authorization", "Basic " + encodedCredentials);

            neutronRestApi.executeMethod(postMethod);

            if (postMethod.getStatusCode() != HttpStatus.SC_CREATED) {
                String errorMessage = responseToErrorMessage(postMethod);
                postMethod.releaseConnection();
                s_logger.error("Failed to create object : " + errorMessage);
                throw new NeutronRestApiException("Failed to create object : " + errorMessage);
            }

            return postMethod.getResponseBodyAsString();
        } catch (NeutronRestApiException e) {
            s_logger.error("NeutronRestApiException caught while trying to execute HTTP Method on the Neutron Controller", e);
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        } catch (IOException e) {
            throw new NeutronRestApiException("Failed to load json response body", e);
        } finally {
            postMethod.releaseConnection();
        }
    }

};