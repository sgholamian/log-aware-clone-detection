//,temp,VerifyNetworkConsumersDisconnectTest.java,268,295,temp,AMQ4607Test.java,270,284
//,3
public class xxx {
    protected void assertExactConsumersConnect(final String brokerName, final int count, final int numChecks, long timeout) throws Exception {
        final ManagementContext context = brokers.get(brokerName).broker.getManagementContext();
        final AtomicInteger stability = new AtomicInteger(0);
        assertTrue("Expected consumers count: " + count + " on: " + brokerName, Wait.waitFor(new Wait.Condition() {
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
        }, timeout));
    }

};