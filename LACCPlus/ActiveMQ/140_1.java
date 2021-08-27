//,temp,NetworkConnector.java,225,235,temp,RuntimeConfigurationBroker.java,87,98
//,3
public class xxx {
    protected void unregisterNetworkBridgeMBean(NetworkBridge bridge) {
        if (!getBrokerService().isUseJmx()) {
            return;
        }
        try {
            ObjectName objectName = createNetworkBridgeObjectName(bridge);
            getBrokerService().getManagementContext().unregisterMBean(objectName);
        } catch (Throwable e) {
            LOG.debug("Network bridge could not be unregistered in JMX: {}", e.getMessage(), e);
        }
    }

};