//,temp,DefaultLRUCacheFactory.java,182,189,temp,CaffeineLRUCache.java,300,307
//,3
public class xxx {
    static <V> void doStop(V value) {
        try {
            // stop service as its evicted from cache
            ServiceHelper.stopService(value);
        } catch (Exception e) {
            LOG.warn("Error stopping service: " + value + ". This exception will be ignored.", e);
        }
    }

};