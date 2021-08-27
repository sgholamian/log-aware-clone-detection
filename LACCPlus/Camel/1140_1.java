//,temp,JmxManagementLifecycleStrategy.java,731,752,temp,JmxManagementLifecycleStrategy.java,665,694
//,3
public class xxx {
    @Override
    public void onThreadPoolRemove(CamelContext camelContext, ThreadPoolExecutor threadPool) {
        if (!initialized) {
            return;
        }

        // lookup the thread pool and remove it from JMX
        Object mtp = managedThreadPools.remove(threadPool);
        if (mtp != null) {
            // skip unmanaged routes
            if (!getManagementStrategy().isManaged(mtp)) {
                LOG.trace("The thread pool is not managed: {}", threadPool);
                return;
            }

            try {
                unmanageObject(mtp);
            } catch (Exception e) {
                LOG.warn("Could not unregister ThreadPool MBean", e);
            }
        }
    }

};