//,temp,VerifyNetworkConsumersDisconnectTest.java,272,293,temp,AMQ4607Test.java,286,305
//,3
public class xxx {
    protected void assertExactConsumersConnect(final String brokerName, Destination destination, final int count, long timeout) throws Exception {
        final ManagementContext context = brokers.get(brokerName).broker.getManagementContext();
        assertTrue("Excepected consumers count: " + count + " on: " + brokerName, Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                try {
                    QueueViewMBean queueViewMBean = (QueueViewMBean) context.newProxyInstance(brokers.get(brokerName).broker.getAdminView().getQueues()[0], QueueViewMBean.class, false);
                    long currentCount = queueViewMBean.getConsumerCount();
                    LOG.info("On " + brokerName + " current consumer count for " + queueViewMBean + ", " + currentCount);
                    if (count != currentCount) {
                        LOG.info("Sub IDs: " + Arrays.asList(queueViewMBean.getSubscriptions()));
                    }
                    return currentCount == count;
                } catch (Exception e) {
                    LOG.warn("Unexpected: " + e, e);
                    return false;
                }
            }
        }, timeout));
    }

};