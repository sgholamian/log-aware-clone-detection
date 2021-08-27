//,temp,JmxManagementLifecycleStrategy.java,420,433,temp,JmxManagementLifecycleStrategy.java,375,387
//,3
public class xxx {
    @Override
    public void onComponentRemove(String name, Component component) {
        // the agent hasn't been started
        if (!initialized) {
            return;
        }
        try {
            Object mc = getManagementObjectStrategy().getManagedObjectForComponent(camelContext, component, name);
            unmanageObject(mc);
        } catch (Exception e) {
            LOG.warn("Could not unregister Component MBean", e);
        }
    }

};