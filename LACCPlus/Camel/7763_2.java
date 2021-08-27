//,temp,SalesforceEndpoint.java,147,164,temp,SalesforceEndpoint.java,128,145
//,2
public class xxx {
    @Override
    protected void doStart() throws Exception {
        try {
            super.doStart();
        } finally {
            // check if this endpoint has its own http client that needs to be started
            HttpClient httpClient = getConfiguration().getHttpClient();
            if (httpClient == null) {
                httpClient = getComponent().getHttpClient();
            }
            if (httpClient != null && getComponent().getHttpClient() != httpClient) {
                final String endpointUri = getEndpointUri();
                LOG.debug("Starting http client for {} ...", endpointUri);
                httpClient.start();
                LOG.debug("Started http client for {}", endpointUri);
            }
        }
    }

};