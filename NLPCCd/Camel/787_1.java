//,temp,sample_1919.java,2,10,temp,sample_6290.java,2,12
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String cacheKey = key.evaluate(exchange, String.class);
if (isValid(cacheManager, cacheName, cacheKey)) {
Ehcache cache = cacheManager.getCache(cacheName);


log.info("replacing message body from cachename for key");
}
}

};