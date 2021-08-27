//,temp,ManagedRegionBroker.java,788,801,temp,ManagedRegionBroker.java,764,786
//,3
public class xxx {
    public ObjectName registerSlowConsumerStrategy(AbortSlowConsumerStrategy strategy) throws MalformedObjectNameException {
        ObjectName objectName = null;
        try {
            objectName = BrokerMBeanSupport.createAbortSlowConsumerStrategyName(brokerObjectName, strategy);
            if (!registeredMBeans.contains(objectName))  {

                AbortSlowConsumerStrategyView view = null;
                if (strategy instanceof AbortSlowAckConsumerStrategy) {
                    view = new AbortSlowAckConsumerStrategyView(this, (AbortSlowAckConsumerStrategy) strategy);
                } else {
                    view = new AbortSlowConsumerStrategyView(this, strategy);
                }

                if (AsyncAnnotatedMBean.registerMBean(asyncInvokeService, mbeanTimeout, managementContext, view, objectName) != null) {
                    registeredMBeans.add(objectName);
                }
            }
        } catch (Exception e) {
            LOG.warn("Failed to register MBean {}", strategy);
            LOG.debug("Failure reason: ", e);
        }
        return objectName;
    }

};