//,temp,BrokerView.java,472,483,temp,BrokerService.java,1412,1428
//,3
public class xxx {
    public Map<String, String> getTransportConnectorURIsAsMap() {
        Map<String, String> answer = new HashMap<>();
        for (TransportConnector connector : transportConnectors) {
            try {
                URI uri = connector.getConnectUri();
                if (uri != null) {
                    String scheme = uri.getScheme();
                    if (scheme != null) {
                        answer.put(scheme.toLowerCase(Locale.ENGLISH), uri.toString());
                    }
                }
            } catch (Exception e) {
                LOG.debug("Failed to read URI to build transportURIsAsMap", e);
            }
        }
        return answer;
    }

};