//,temp,sample_7011.java,2,10,temp,sample_7012.java,2,11
//,3
public class xxx {
public synchronized int putImage(byte[] image) {
while (cache.size() >= cacheSize) {
Integer keyToRemove = fifoQueue.remove(0);
cache.remove(keyToRemove);


log.info("remove image from cache key");
}
}

};