//,temp,CaffeineLRUCacheFactory.java,163,168,temp,DefaultLRUCacheFactory.java,96,100
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUSoftCache(int maximumCacheSize) {
        LOG.trace("Creating LRUSoftCache with maximumCacheSize: {}", maximumCacheSize);
        return new SimpleLRUCache<>(maximumCacheSize);
    }

};