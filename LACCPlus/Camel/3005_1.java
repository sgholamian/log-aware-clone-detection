//,temp,CaffeineLRUCacheFactory.java,134,138,temp,DefaultLRUCacheFactory.java,67,71
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUSoftCache(int maximumCacheSize) {
        LOG.trace("Creating LRUSoftCache with maximumCacheSize: {}", maximumCacheSize);
        return new CaffeineLRUSoftCache<>(maximumCacheSize);
    }

};