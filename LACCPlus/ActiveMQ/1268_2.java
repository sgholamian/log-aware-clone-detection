//,temp,TopicStorePrefetch.java,126,134,temp,QueueStorePrefetch.java,82,90
//,2
public class xxx {
    @Override
    protected synchronized long getStoreMessageSize() {
        try {
            return this.store.getMessageSize();
        } catch (IOException e) {
            LOG.error("Failed to get message size", e);
            throw new RuntimeException(e);
        }
    }

};