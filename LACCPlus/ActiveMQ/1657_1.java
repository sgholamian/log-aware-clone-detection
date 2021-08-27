//,temp,VerifyNetworkConsumersDisconnectTest.java,272,293,temp,AMQ4607Test.java,286,305
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                try {
                    QueueViewMBean queueViewMBean = (QueueViewMBean) context.newProxyInstance(brokers.get(brokerName).broker.getAdminView().getQueues()[0], QueueViewMBean.class, false);
                    long currentCount = queueViewMBean.getConsumerCount();
                    LOG.info("On " + brokerName + " current consumer count for " + queueViewMBean + ", " + currentCount);
                    LinkedList<String> consumerIds = new LinkedList<String>();
                    for (ObjectName objectName : queueViewMBean.getSubscriptions()) {
                        consumerIds.add(objectName.getKeyProperty("consumerId"));
                    }
                    LOG.info("Sub IDs: " + consumerIds);
                    if (currentCount == count) {
                        stability.incrementAndGet();
                    } else {
                        stability.set(0);
                    }
                    return stability.get() > numChecks;
                } catch (Exception e) {
                    LOG.warn(": ", e);
                    return false;
                }
            }

};