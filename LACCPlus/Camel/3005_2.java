//,temp,CaffeineLRUCacheFactory.java,134,138,temp,DefaultLRUCacheFactory.java,67,71
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUCache(int initialCapacity, int maximumCacheSize) {
        LOG.trace("Creating LRUCache with initialCapacity: {}, maximumCacheSize: {}", initialCapacity, maximumCacheSize);
        return new SimpleLRUCache<>(initialCapacity, maximumCacheSize);
    }

};