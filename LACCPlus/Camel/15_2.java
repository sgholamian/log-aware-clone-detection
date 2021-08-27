//,temp,CaffeineLRUCacheFactory.java,191,195,temp,DefaultLRUCacheFactory.java,128,132
//,2
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUWeakCache(int initialCapacity, int maximumCacheSize) {
        LOG.trace("Creating LRUCache with initialCapacity: {}, maximumCacheSize: {}", initialCapacity, maximumCacheSize);
        return new SimpleLRUCache<>(initialCapacity, maximumCacheSize);
    }

};