//,temp,CaffeineLRUCacheFactory.java,206,211,temp,DefaultLRUCacheFactory.java,53,57
//,3
public class xxx {
    @Override
    public <K, V> Map<K, V> createLRUCache(int maximumCacheSize, Consumer<V> onEvict) {
        LOG.trace("Creating LRUCache with maximumCacheSize: {}", maximumCacheSize);
        return new SimpleLRUCache<>(16, maximumCacheSize, onEvict);
    }

};