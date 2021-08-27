//,temp,sample_183.java,2,11,temp,sample_181.java,2,8
//,3
public class xxx {
public boolean isValid(CacheManager cacheManager, String cacheName, String key) {
if (!cacheManager.cacheExists(cacheName)) {


log.info("no existing cache found with name please ensure a cache is first instantiated using a cache consumer or cache producer replacement will not be performed since the cache does not presently exist");
}
}

};