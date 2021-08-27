//,temp,CaffeineLRUCacheFactory.java,148,152,temp,DefaultLRUCacheFactory.java,102,106
//,2
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUSoftCache(int initialCapacity, int maximumCacheSize) {
        LOG.trace("Creating LRUSoftCache with initialCapacity: {}, maximumCacheSize: {}", initialCapacity, maximumCacheSize);
        return new CaffeineLRUSoftCache<>(initialCapacity, maximumCacheSize);
    }

};