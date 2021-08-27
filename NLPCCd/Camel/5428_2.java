//,temp,sample_5722.java,2,11,temp,sample_4351.java,2,11
//,3
public class xxx {
public void closeMemcachedClient(String key, MemcachedClient memcachedClient) {
try {
memcachedClient.shutdown();
memcachedClientCache.remove(key);
} catch (Exception e) {


log.info("failed to close client connection to");
}
}

};