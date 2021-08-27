//,temp,CaffeineLRUCacheFactory.java,105,109,temp,CaffeineLRUCacheFactory.java,79,83
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUCache(int maximumCacheSize) {
        LOG.trace("Creating LRUCache with maximumCacheSize: {}", maximumCacheSize);
        return new CaffeineLRUCache<>(maximumCacheSize);
    }

};