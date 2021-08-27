//,temp,sample_470.java,2,18,temp,sample_471.java,2,17
//,3
public class xxx {
public void dummy_method(){
while (true) {
LlapDataBuffer oldVal = subCache.getCache().putIfAbsent(offset, buffer);
if (oldVal == null) {
cachePolicy.cache(buffer, priority);
if (qfCounters != null) {
qfCounters.recordAllocBytes(buffer.byteBuffer.remaining(), buffer.allocSize);
}
break;
}
if (LlapIoImpl.CACHE_LOGGER.isTraceEnabled()) {


log.info("trying to cache when the chunk is already cached for base old new");
}
}
}

};