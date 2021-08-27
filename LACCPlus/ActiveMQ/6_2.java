//,temp,SubQueueSelectorCacheBroker.java,124,133,temp,BrokerService.java,2335,2344
//,3
public class xxx {
    protected void unregisterNetworkConnectorMBean(NetworkConnector connector) {
        if (isUseJmx()) {
            try {
                ObjectName objectName = createNetworkConnectorObjectName(connector);
                getManagementContext().unregisterMBean(objectName);
            } catch (Exception e) {
                LOG.warn("Network Connector could not be unregistered from JMX due {}. This exception is ignored.", e.getMessage(), e);
            }
        }
    }

};