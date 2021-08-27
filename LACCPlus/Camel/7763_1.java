//,temp,SalesforceEndpoint.java,147,164,temp,SalesforceEndpoint.java,128,145
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        try {
            super.doStop();
        } finally {
            // check if this endpoint has its own http client that needs to be stopped
            HttpClient httpClient = getConfiguration().getHttpClient();
            if (httpClient == null) {
                httpClient = getComponent().getHttpClient();
            }
            if (httpClient != null && getComponent().getHttpClient() != httpClient) {
                final String endpointUri = getEndpointUri();
                LOG.debug("Stopping http client for {} ...", endpointUri);
                httpClient.stop();
                LOG.debug("Stopped http client for {}", endpointUri);
            }
        }
    }

};