//,temp,BrokerView.java,472,483,temp,BrokerService.java,1412,1428
//,3
public class xxx {
    @Override
    public Map<String, String> getTransportConnectors() {
        Map<String, String> answer = new HashMap<String, String>();
        try {
            for (TransportConnector connector : brokerService.getTransportConnectors()) {
                answer.put(connector.getName(), connector.getConnectUri().toString());
            }
        } catch (Exception e) {
            LOG.debug("Failed to read URI to build transport connectors map", e);
        }
        return answer;
    }

};