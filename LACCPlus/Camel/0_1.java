//,temp,DefaultLRUCacheFactory.java,122,126,temp,DefaultLRUCacheFactory.java,41,45
//,2
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUWeakCache(int maximumCacheSize) {
        LOG.trace("Creating LRUWeakCache with maximumCacheSize: {}", maximumCacheSize);
        return new SimpleLRUCache<>(maximumCacheSize);
    }

};