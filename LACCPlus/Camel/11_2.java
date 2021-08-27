//,temp,CaffeineLRUCacheFactory.java,120,125,temp,DefaultLRUCacheFactory.java,82,87
//,2
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUCache(int initialCapacity, int maximumCacheSize, boolean stopOnEviction) {
        LOG.trace("Creating LRUCache with initialCapacity: {}, maximumCacheSize: {}, stopOnEviction: {}", initialCapacity,
                maximumCacheSize, stopOnEviction);
        return new SimpleLRUCache<>(initialCapacity, maximumCacheSize, stopOnEviction);
    }

};