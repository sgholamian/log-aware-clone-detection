//,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,142,155,temp,DurableSubProcessMultiRestartTest.java,132,145
//,2
public class xxx {
    private void restartBroker() throws Exception {
        LOG.info("Broker restart: waiting for components.");

        processLock.writeLock().lock();
        try {
            destroyBroker();
            startBroker(false);

            restartCount++;
            LOG.info("Broker restarted. count: " + restartCount);
        } finally {
            processLock.writeLock().unlock();
        }
    }

};