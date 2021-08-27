//,temp,JmxManagementStrategy.java,100,110,temp,JmxManagementStrategy.java,87,98
//,3
public class xxx {
    @Override
    public boolean isManagedName(Object name) {
        try {
            if (name instanceof ObjectName) {
                return getManagementAgent().isRegistered((ObjectName) name);
            }
        } catch (Exception e) {
            LOG.warn("Cannot check whether the managed object is registered. This exception will be ignored.", e);
        }
        return false;
    }

};