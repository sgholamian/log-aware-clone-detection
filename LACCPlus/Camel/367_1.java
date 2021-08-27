//,temp,CaffeineLRUCacheFactory.java,206,211,temp,DefaultLRUCacheFactory.java,53,57
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUWeakCache(int initialCapacity, int maximumCacheSize, boolean stopOnEviction) {
        LOG.trace("Creating LRUWeakCache with initialCapacity: {}, maximumCacheSize: {}, stopOnEviction: {}", initialCapacity,
                maximumCacheSize, stopOnEviction);
        return new CaffeineLRUWeakCache<>(initialCapacity, maximumCacheSize, stopOnEviction);
    }

};