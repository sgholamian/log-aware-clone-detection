//,temp,StoreDurableSubscriberCursor.java,247,260,temp,StoreQueueCursor.java,128,137
//,3
public class xxx {
    @Override
    public synchronized boolean hasNext() {
        try {
            getNextCursor();
        } catch (Exception e) {
            LOG.error("Failed to get current cursor ", e);
            throw new RuntimeException(e);
       }
       return currentCursor != null ? currentCursor.hasNext() : false;
    }

};