//,temp,CaffeineLRUCacheFactory.java,163,168,temp,DefaultLRUCacheFactory.java,96,100
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUSoftCache(int initialCapacity, int maximumCacheSize, boolean stopOnEviction) {
        LOG.trace("Creating LRUSoftCache with initialCapacity: {}, maximumCacheSize: {}, stopOnEviction: {}", initialCapacity,
                maximumCacheSize, stopOnEviction);
        return new CaffeineLRUSoftCache<>(initialCapacity, maximumCacheSize, stopOnEviction);
    }

};