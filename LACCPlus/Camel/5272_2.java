//,temp,JmxManagementStrategy.java,100,110,temp,JmxManagementStrategy.java,87,98
//,3
public class xxx {
    @Override
    public boolean isManaged(Object managedObject) {
        try {
            ObjectName name = getManagementObjectNameStrategy().getObjectName(managedObject);
            if (name != null) {
                return getManagementAgent().isRegistered(name);
            }
        } catch (Exception e) {
            LOG.warn("Cannot check whether the managed object is registered. This exception will be ignored.", e);
        }
        return false;
    }

};