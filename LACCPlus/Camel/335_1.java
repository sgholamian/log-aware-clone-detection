//,temp,DefaultLRUCacheFactory.java,182,189,temp,CaffeineLRUCache.java,300,307
//,3
public class xxx {
    <V> void doStop(V value) {
        try {
            // stop service as its evicted from cache
            ServiceHelper.stopService(value);
        } catch (Exception e) {
            LOG.warn("Error stopping service: {}. This exception will be ignored.", value, e);
        }
    }

};