//,temp,sample_1502.java,2,19,temp,sample_7790.java,2,13
//,3
public class xxx {
private synchronized void scheduleOldDBsForEviction() {
long evictionThreshold = computeCurrentCheckMillis(currentTimeMillis() - getTimeToLive());
Iterator<Entry<Long, DB>> iterator = rollingdbs.entrySet().iterator();
while (iterator.hasNext()) {
Entry<Long, DB> entry = iterator.next();
if (entry.getKey() < evictionThreshold) {


log.info("scheduling eviction for");
}
}
}

};