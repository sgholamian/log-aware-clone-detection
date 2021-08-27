//,temp,DefaultLRUCacheFactory.java,134,139,temp,DefaultLRUCacheFactory.java,108,113
//,2
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUWeakCache(int initialCapacity, int maximumCacheSize, boolean stopOnEviction) {
        LOG.trace("Creating LRUCache with initialCapacity: {}, maximumCacheSize: {}, stopOnEviction: {}", initialCapacity,
                maximumCacheSize, stopOnEviction);
        return new SimpleLRUCache<>(initialCapacity, maximumCacheSize, stopOnEviction);
    }

};