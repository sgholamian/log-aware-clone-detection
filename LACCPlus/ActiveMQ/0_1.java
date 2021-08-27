//,temp,ManagedTransportConnection.java,104,113,temp,ManagedTransportConnection.java,93,102
//,3
public class xxx {
    protected void unregisterMBean(ObjectName name) {
        if (name != null) {
            try {
                managementContext.unregisterMBean(name);
            } catch (Throwable e) {
                LOG.warn("Failed to unregister MBean {}", name);
                LOG.debug("Failure reason: ", e);
            }
        }
    }

};