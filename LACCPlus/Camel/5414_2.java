//,temp,MongoDbTailTrackingManager.java,77,89,temp,MongoDbTailTrackingManager.java,63,75
//,3
public class xxx {
    public synchronized void persistToStore() {
        if (!config.persistent || lastVal == null) {
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Persisting lastVal={} to store, collection: {}", lastVal, config.collection);
        }

        Bson updateObj = Updates.set(config.field, lastVal);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
        trackingObj = dbCol.findOneAndUpdate(trackingObj, updateObj, options);
    }

};