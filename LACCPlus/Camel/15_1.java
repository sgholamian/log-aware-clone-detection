//,temp,CaffeineLRUCacheFactory.java,191,195,temp,DefaultLRUCacheFactory.java,128,132
//,2
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUWeakCache(int initialCapacity, int maximumCacheSize) {
        LOG.trace("Creating LRUWeakCache with initialCapacity: {}, maximumCacheSize: {}", initialCapacity, maximumCacheSize);
        return new CaffeineLRUWeakCache<>(initialCapacity, maximumCacheSize);
    }

};