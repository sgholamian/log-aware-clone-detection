//,temp,JmxManagementLifecycleStrategy.java,420,433,temp,JmxManagementLifecycleStrategy.java,375,387
//,3
public class xxx {
    @Override
    public void onEndpointRemove(Endpoint endpoint) {
        // the agent hasn't been started
        if (!initialized) {
            return;
        }

        try {
            Object me = getManagementObjectStrategy().getManagedObjectForEndpoint(camelContext, endpoint);
            unmanageObject(me);
        } catch (Exception e) {
            LOG.warn("Could not unregister Endpoint MBean for endpoint: " + endpoint + ". This exception will be ignored.", e);
        }
    }

};