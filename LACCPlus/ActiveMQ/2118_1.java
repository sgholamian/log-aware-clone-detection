//,temp,HTTPDiscoveryAgent.java,116,128,temp,HTTPDiscoveryAgent.java,103,114
//,3
public class xxx {
    @SuppressWarnings("unused")
    synchronized private void doUnRegister(String service) {
        String url = registryURL;
        try {
            HttpDelete method = new HttpDelete(url);
            method.addHeader("service", service);
            ResponseHandler<String> handler = new BasicResponseHandler();
            String responseBody = httpClient.execute(method, handler);
            LOG.debug("DELETE to " + url + " got a " + responseBody);
        } catch (Exception e) {
            LOG.debug("DELETE to " + url + " failed with: " + e);
        }
    }

};