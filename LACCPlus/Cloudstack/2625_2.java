//,temp,Action.java,188,222,temp,Action.java,67,112
//,3
public class xxx {
    public String executeGet(final String uri, final Map<String, String> parameters) throws NeutronRestApiException {
        try {
            validateCredentials();
        } catch (NeutronInvalidCredentialsException e) {
            throw new NeutronRestApiException("Invalid credentials!", e);
        }

        NeutronRestFactory factory = NeutronRestFactory.getInstance();

        NeutronRestApi neutronRestApi = factory.getNeutronApi(GetMethod.class);
        GetMethod getMethod = (GetMethod) neutronRestApi.createMethod(url, uri);

        try {
            getMethod.setRequestHeader(CONTENT_TYPE, JSON_CONTENT_TYPE);

            String encodedCredentials = encodeCredentials();
            getMethod.setRequestHeader("Authorization", "Basic " + encodedCredentials);

            if (parameters != null && !parameters.isEmpty()) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(parameters.size());
                for (Entry<String, String> e : parameters.entrySet()) {
                    nameValuePairs.add(new NameValuePair(e.getKey(), e.getValue()));
                }
                getMethod.setQueryString(nameValuePairs.toArray(new NameValuePair[0]));
            }

            neutronRestApi.executeMethod(getMethod);

            if (getMethod.getStatusCode() != HttpStatus.SC_OK) {
                String errorMessage = responseToErrorMessage(getMethod);
                getMethod.releaseConnection();
                s_logger.error("Failed to retrieve object : " + errorMessage);
                throw new NeutronRestApiException("Failed to retrieve object : " + errorMessage);
            }

            return getMethod.getResponseBodyAsString();

        } catch (NeutronRestApiException e) {
            s_logger.error("NeutronRestApiException caught while trying to execute HTTP Method on the Neutron Controller", e);
            throw new NeutronRestApiException("API call to Neutron Controller Failed", e);
        } catch (IOException e) {
            throw new NeutronRestApiException(e);
        } finally {
            getMethod.releaseConnection();
        }
    }

};