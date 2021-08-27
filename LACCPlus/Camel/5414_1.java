//,temp,MongoDbTailTrackingManager.java,77,89,temp,MongoDbTailTrackingManager.java,63,75
//,3
public class xxx {
    public synchronized Object recoverFromStore() {
        if (!config.persistent) {
            return null;
        }

        lastVal = dbCol.find(trackingObj).first().get(config.field);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Recovered lastVal={} from store, collection: {}", lastVal, config.collection);
        }

        return lastVal;
    }

};