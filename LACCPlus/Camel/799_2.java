//,temp,MBeanInfoAssembler.java,77,87,temp,IntrospectionSupport.java,124,131
//,3
public class xxx {
    public static void clearCache() {
        if (LOG.isDebugEnabled() && CACHE instanceof LRUCache) {
            LRUCache localCache = (LRUCache) IntrospectionSupport.CACHE;
            LOG.debug("Clearing cache[size={}, hits={}, misses={}, evicted={}]", localCache.size(), localCache.getHits(),
                    localCache.getMisses(), localCache.getEvicted());
        }
        CACHE.clear();
    }

};