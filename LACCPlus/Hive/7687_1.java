//,temp,SerDeLowLevelCacheImpl.java,701,714,temp,SimpleBufferManager.java,50,58
//,3
public class xxx {
  private void unlockBuffer(LlapSerDeDataBuffer buffer, boolean handleLastDecRef) {
    boolean isLastDecref = (buffer.decRef() == 0);
    if (handleLastDecRef && isLastDecref) {
      if (buffer.isCached) {
        cachePolicy.notifyUnlock(buffer);
      } else {
        if (LlapIoImpl.CACHE_LOGGER.isTraceEnabled()) {
          LlapIoImpl.CACHE_LOGGER.trace("Deallocating {} that was not cached", buffer);
        }
        allocator.deallocate(buffer);
      }
    }
    metrics.decrCacheNumLockedBuffers();
  }

};