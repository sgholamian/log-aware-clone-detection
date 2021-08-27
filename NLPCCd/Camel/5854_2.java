//,temp,sample_5768.java,2,14,temp,sample_5767.java,2,11
//,3
public class xxx {
private void performCacheOperation(Exchange exchange, String operation, String key) throws Exception {
if (checkIsEqual(operation, CacheConstants.CACHE_OPERATION_URL_ADD)) {
Element element = createElementFromBody(key, exchange, CacheConstants.CACHE_OPERATION_ADD);
cache.put(element);
} else if (checkIsEqual(operation, CacheConstants.CACHE_OPERATION_URL_UPDATE)) {


log.info("updating an element with key into the cache");
}
}

};