//,temp,ZooKeeperConsumer.java,106,113,temp,ZooKeeperConsumer.java,97,104
//,2
public class xxx {
    private void initializeChildListingConsumer(String node) {
        if (!shuttingDown) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("Initializing child listing of node '%s'", node));
            }
            addBasicChildListingSequence(node);
        }
    }

};