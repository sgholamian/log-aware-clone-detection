//,temp,QueueStorePrefetch.java,97,106,temp,QueueStorePrefetch.java,70,80
//,3
public class xxx {
    @Override
    protected synchronized boolean isStoreEmpty() {
        try {
            return this.store.isEmpty();

        } catch (Exception e) {
            LOG.error("Failed to get message count", e);
            throw new RuntimeException(e);
        }
    }

};