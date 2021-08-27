//,temp,sample_183.java,2,11,temp,sample_181.java,2,8
//,3
public class xxx {
public boolean isValid(CacheManager cacheManager, String cacheName, String key) {
if (!cacheManager.cacheExists(cacheName)) {
return false;
}
if (LOG.isTraceEnabled()) {


log.info("cache currently contains elements");
}
}

};