//,temp,LlapAllocatorBuffer.java,205,221,temp,LlapAllocatorBuffer.java,175,199
//,3
public class xxx {
  public int invalidateAndRelease() {
    boolean result;
    long oldValue, newValue;
    do {
      result = false;
      oldValue = state.get();
      if (State.getRefCount(oldValue) != 0) {
        throw new AssertionError("Refcount is " + State.getRefCount(oldValue));
      }
      if (State.hasFlags(oldValue, State.FLAG_EVICTED)) {
        return -1; // Concurrent force-eviction - ignore.
      }
      newValue = State.setFlag(oldValue, State.FLAG_EVICTED);
      if (!State.hasFlags(oldValue, State.FLAG_MOVING)) {
        // No move pending, the allocator can release.
        newValue = State.setFlag(newValue, State.FLAG_REMOVED | State.FLAG_MEM_RELEASED);
        result = true;
      }
    } while (!state.compareAndSet(oldValue, newValue));
    if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
      LlapIoImpl.LOCKING_LOGGER.trace("Invalidated {} due to direct deallocation", this);
    }
    // Arena cannot change after we have marked it as released.
    return result ? State.getArena(oldValue) : -1;
  }

};