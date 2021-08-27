//,temp,sample_2765.java,2,12,temp,sample_2764.java,2,12
//,2
public class xxx {
public Ehcache initializeCache() {
CacheManager cacheManager = getCacheManagerFactory().getInstance();
Ehcache cache;
if (cacheManager.cacheExists(config.getCacheName())) {
if (LOG.isTraceEnabled()) {


log.info("cache currently contains elements");
}
}
}

};