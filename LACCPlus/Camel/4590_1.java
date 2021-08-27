//,temp,JmxManagementLifecycleStrategy.java,696,729,temp,JmxManagementLifecycleStrategy.java,435,468
//,3
public class xxx {
    @Override
    public void onThreadPoolAdd(
            CamelContext camelContext, ThreadPoolExecutor threadPool, String id,
            String sourceId, String routeId, String threadPoolProfileId) {

        if (!initialized) {
            // pre register so we can register later when we have been initialized
            preServices.add(lf -> lf.onThreadPoolAdd(camelContext, threadPool, id, sourceId, routeId, threadPoolProfileId));
            return;
        }

        if (!shouldRegister(threadPool, null)) {
            // avoid registering if not needed
            return;
        }

        Object mtp = getManagementObjectStrategy().getManagedObjectForThreadPool(camelContext, threadPool, id, sourceId,
                routeId, threadPoolProfileId);

        // skip already managed services, for example if a route has been restarted
        if (getManagementStrategy().isManaged(mtp)) {
            LOG.trace("The thread pool is already managed: {}", threadPool);
            return;
        }

        try {
            manageObject(mtp);
            // store a reference so we can unmanage from JMX when the thread pool is removed
            // we need to keep track here, as we cannot re-construct the thread pool ObjectName when removing the thread pool
            managedThreadPools.put(threadPool, mtp);
        } catch (Exception e) {
            LOG.warn("Could not register thread pool: " + threadPool + " as ThreadPool MBean.", e);
        }
    }

};