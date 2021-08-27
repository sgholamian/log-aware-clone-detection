//,temp,ZooKeeperProducer.java,264,272,temp,ZooKeeperProducer.java,203,212
//,3
public class xxx {
    private void logStoreComplete(String path, Stat statistics) {
        if (LOG.isDebugEnabled()) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(format("Stored data to node '%s', and receive statistics %s", path, statistics));
            } else {
                LOG.debug(format("Stored data to node '%s'", path));
            }
        }
    }

};