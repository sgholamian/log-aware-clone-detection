//,temp,NetworkConnector.java,225,235,temp,RuntimeConfigurationBroker.java,87,98
//,3
public class xxx {
    @Override
    protected void registerMbean() {
        if (getBrokerService().isUseJmx()) {
            ManagementContext managementContext = getBrokerService().getManagementContext();
            try {
                objectName = new ObjectName(getBrokerService().getBrokerObjectName().toString() + objectNamePropsAppendage);
                managementContext.registerMBean(new RuntimeConfigurationView(this), objectName);
            } catch (Exception ignored) {
                LOG.debug("failed to register RuntimeConfigurationMBean", ignored);
            }
        }
    }

};