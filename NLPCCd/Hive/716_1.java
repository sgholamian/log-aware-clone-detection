//,temp,sample_473.java,2,15,temp,sample_4200.java,2,15
//,3
public class xxx {
private void unlockBuffer(LlapDataBuffer buffer, boolean handleLastDecRef) {
boolean isLastDecref = (buffer.decRef() == 0);
if (handleLastDecRef && isLastDecref) {
if (buffer.declaredCachedLength != LlapDataBuffer.UNKNOWN_CACHED_LENGTH) {
cachePolicy.notifyUnlock(buffer);
} else {
if (LlapIoImpl.CACHE_LOGGER.isTraceEnabled()) {


log.info("deallocating that was not cached");
}
}
}
}

};