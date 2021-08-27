//,temp,ManagedRegionBroker.java,363,391,temp,ManagedRegionBroker.java,312,334
//,3
public class xxx {
    protected void registerProducer(ObjectName key, ActiveMQDestination dest, ProducerView view) throws Exception {

        if (dest != null) {
            if (dest.isQueue()) {
                if (dest.isTemporary()) {
                    temporaryQueueProducers.put(key, view);
                } else {
                    queueProducers.put(key, view);
                }
            } else {
                if (dest.isTemporary()) {
                    temporaryTopicProducers.put(key, view);
                } else {
                    topicProducers.put(key, view);
                }
            }
        } else {
            dynamicDestinationProducers.put(key, view);
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