//,temp,MBeanInfoAssembler.java,77,87,temp,IntrospectionSupport.java,124,131
//,3
public class xxx {
    @Override
    public void stop() {
        if (cache != null) {
            if (LOG.isDebugEnabled() && cache instanceof LRUCache) {
                LRUCache cache = (LRUCache) this.cache;
                LOG.debug("Clearing cache[size={}, hits={}, misses={}, evicted={}]", cache.size(), cache.getHits(),
                        cache.getMisses(), cache.getEvicted());
            }
            cache.clear();
        }
    }

};