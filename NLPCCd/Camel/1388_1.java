//,temp,sample_3573.java,2,15,temp,sample_3572.java,2,13
//,3
public class xxx {
public <K, V> BasicCache<K, V> getCache(String cacheName, boolean forceReturnValue) {
if (isCacheContainerRemote()) {
BasicCache<K, V> cache;
if (ObjectHelper.isEmpty(cacheName)) {
cache = InfinispanUtil.asRemote(cacheContainer).getCache(forceReturnValue);
cacheName = cache.getName();
} else {
cache = InfinispanUtil.asRemote(cacheContainer).getCache(cacheName, forceReturnValue);
}


log.info("cache");
}
}

};