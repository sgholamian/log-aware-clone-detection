//,temp,SerDeLowLevelCacheImpl.java,701,714,temp,SimpleBufferManager.java,50,58
//,3
public class xxx {
  private void unlockBuffer(LlapAllocatorBuffer buffer) {
    if (buffer.decRef() == 0) {
      if (LlapIoImpl.CACHE_LOGGER.isTraceEnabled()) {
        LlapIoImpl.CACHE_LOGGER.trace("Deallocating {} that was not cached", buffer);
      }
      allocator.deallocate(buffer);
    }
    metrics.decrCacheNumLockedBuffers();
  }

};