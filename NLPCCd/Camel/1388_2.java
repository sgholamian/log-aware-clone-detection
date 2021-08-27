//,temp,sample_3573.java,2,15,temp,sample_3572.java,2,13
//,3
public class xxx {
public <K, V> BasicCache<K, V> getCache(String cacheName) {
BasicCache<K, V> cache;
if (ObjectHelper.isEmpty(cacheName)) {
cache = cacheContainer.getCache();
cacheName = cache.getName();
} else {
cache = cacheContainer.getCache(cacheName);
}


log.info("cache");
}

};