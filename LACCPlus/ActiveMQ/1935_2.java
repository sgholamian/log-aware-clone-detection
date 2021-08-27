//,temp,TempStoreDataCleanupTest.java,168,178,temp,MemoryUsageCleanupTest.java,162,172
//,2
public class xxx {
    public void destroyQueue() {
        try {
            Broker broker = this.broker.getBroker();
            if (!broker.isStopped()) {
                LOG.info("Removing: " + queueName);
                broker.removeDestination(this.broker.getAdminConnectionContext(), new ActiveMQQueue(queueName), 10);
            }
        } catch (Exception e) {
            LOG.warn("Got an error while removing the test queue", e);
        }
    }

};