//,temp,VerifyNetworkConsumersDisconnectTest.java,268,295,temp,AMQ4607Test.java,270,284
//,3
public class xxx {
    protected void assertExactMessageCount(final String brokerName, Destination destination, final int count, long timeout) throws Exception {
        ManagementContext context = brokers.get(brokerName).broker.getManagementContext();
        final QueueViewMBean queueViewMBean = (QueueViewMBean) context.newProxyInstance(brokers.get(brokerName).broker.getAdminView().getQueues()[0], QueueViewMBean.class, false);
        assertTrue("Excepected queue depth: " + count + " on: " + brokerName, Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                long currentCount = queueViewMBean.getQueueSize();
                LOG.info("On " + brokerName + " current queue size for " + queueViewMBean + ", " + currentCount);
                if (count != currentCount) {
                    LOG.info("Sub IDs: " + Arrays.asList(queueViewMBean.getSubscriptions()));
                }
                return currentCount == count;
            }
        }, timeout));
    }

};