//,temp,ManagedTransportConnection.java,104,113,temp,ManagedTransportConnection.java,93,102
//,3
public class xxx {
    protected void registerMBean(ObjectName name) {
        if (name != null) {
            try {
                AnnotatedMBean.registerMBean(managementContext, mbean, name);
            } catch (Throwable e) {
                LOG.warn("Failed to register MBean {}", name);
                LOG.debug("Failure reason: ", e);
            }
        }
    }

};