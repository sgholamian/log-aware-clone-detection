//,temp,sample_5943.java,2,19,temp,sample_5787.java,2,16
//,3
public class xxx {
public void run() {
long currentTime = clock.getTime();
Iterator<Map.Entry<String, CacheEntry>> iterator = cache.entrySet().iterator();
while (iterator.hasNext()) {
Map.Entry<String, CacheEntry> entry = iterator.next();
if (currentTime > entry.getValue().resolveTime + CachedResolver.this.expiryIntervalMs) {
iterator.remove();
if (LOG.isDebugEnabled()) {


log.info("expired after secs");
}
}
}
}

};