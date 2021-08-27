//,temp,TopicStorePrefetch.java,126,134,temp,QueueStorePrefetch.java,82,90
//,2
public class xxx {
    @Override
    protected synchronized boolean isStoreEmpty() {
        try {
            return this.store.isEmpty();
        } catch (Exception e) {
            LOG.error("Failed to determine if store is empty", e);
            throw new RuntimeException(e);
        }
    }

};