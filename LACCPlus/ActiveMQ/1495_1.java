//,temp,ThreeBrokerQueueNetworkTest.java,634,643,temp,ThreeBrokerQueueNetworkTest.java,622,632
//,3
public class xxx {
    private void logConsumerCount(BrokerService broker, int count, final Destination dest) throws Exception {
        final RegionBroker regionBroker = (RegionBroker) broker.getRegionBroker();
        waitFor(new Condition() {
            public boolean isSatisified() throws Exception {
                return !regionBroker.getDestinations(ActiveMQDestination.transform(dest)).isEmpty();
            }
        });
        Queue internalQueue = (Queue) regionBroker.getDestinations(ActiveMQDestination.transform(dest)).iterator().next();
        LOG.info("Verify: consumer count on " + broker.getBrokerName() + " matches:" + count + ", val:" + internalQueue.getConsumers().size());
    }

};