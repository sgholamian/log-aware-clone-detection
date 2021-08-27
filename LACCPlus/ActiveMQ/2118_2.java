//,temp,HTTPDiscoveryAgent.java,116,128,temp,HTTPDiscoveryAgent.java,103,114
//,3
public class xxx {
    synchronized private void doRegister(String service) {
        String url = registryURL;
        try {
            HttpPut method = new HttpPut(url);
            method.addHeader("service", service);
            ResponseHandler<String> handler = new BasicResponseHandler();
            String responseBody = httpClient.execute(method, handler);
            LOG.debug("PUT to " + url + " got a " + responseBody);
        } catch (Exception e) {
            LOG.debug("PUT to " + url + " failed with: " + e);
        }
    }

};