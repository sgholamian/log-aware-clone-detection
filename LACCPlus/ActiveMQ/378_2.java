//,temp,ManagedRegionBroker.java,363,391,temp,ManagedRegionBroker.java,312,334
//,3
public class xxx {
    protected void registerDestination(ObjectName key, ActiveMQDestination dest, DestinationView view) throws Exception {
        if (dest.isQueue()) {
            if (dest.isTemporary()) {
                temporaryQueues.put(key, view);
            } else {
                queues.put(key, view);
            }
        } else {
            if (dest.isTemporary()) {
                temporaryTopics.put(key, view);
            } else {
                topics.put(key, view);
            }
        }
        try {
            if (AsyncAnnotatedMBean.registerMBean(asyncInvokeService, mbeanTimeout, managementContext, view, key) != null) {
                registeredMBeans.add(key);
            }
        } catch (Throwable e) {
            LOG.warn("Failed to register MBean {}", key);
            LOG.debug("Failure reason: ", e);
        }
    }

};