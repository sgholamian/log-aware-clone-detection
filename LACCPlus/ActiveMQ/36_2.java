//,temp,QueueStorePrefetch.java,97,106,temp,QueueStorePrefetch.java,70,80
//,3
public class xxx {
    @Override
    protected synchronized int getStoreSize() {
        try {
            int result = this.store.getMessageCount();
            return result;

        } catch (IOException e) {
            LOG.error("Failed to get message count", e);
            throw new RuntimeException(e);
        }
    }

};