//,temp,SubQueueSelectorCacheBroker.java,124,133,temp,BrokerService.java,2335,2344
//,3
public class xxx {
    private void unregisterMBeans() {
        BrokerService broker = getBrokerService();
        if (broker.isUseJmx() && this.objectName != null) {
            try {
                broker.getManagementContext().unregisterMBean(objectName);
            } catch (JMException e) {
                LOG.warn("Trying uninstall VirtualDestinationSelectorCache; couldn't uninstall mbeans, continuting...");
            }
        }
    }

};