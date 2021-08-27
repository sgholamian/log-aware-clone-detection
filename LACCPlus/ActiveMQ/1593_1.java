//,temp,ManagedRegionBroker.java,788,801,temp,ManagedRegionBroker.java,764,786
//,3
public class xxx {
    public void registerRecoveredTransactionMBean(XATransaction transaction) {
        try {
            ObjectName objectName = BrokerMBeanSupport.createXATransactionName(brokerObjectName, transaction);
            if (!registeredMBeans.contains(objectName))  {
                RecoveredXATransactionView view = new RecoveredXATransactionView(this, transaction);
                if (AsyncAnnotatedMBean.registerMBean(asyncInvokeService, mbeanTimeout, managementContext, view, objectName) != null) {
                    registeredMBeans.add(objectName);
                }
            }
        } catch (Exception e) {
            LOG.warn("Failed to register prepared transaction MBean {}", transaction);
            LOG.debug("Failure reason: ", e);
        }
    }

};