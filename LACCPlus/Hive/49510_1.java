//,temp,LlapAllocatorBuffer.java,154,167,temp,LlapAllocatorBuffer.java,130,146
//,3
public class xxx {
  @Override
  public int invalidate() {
    while (true) {
      long oldValue = state.get();
      if (State.getRefCount(oldValue) != 0) return INVALIDATE_FAILED;
      if (State.hasFlags(oldValue, State.FLAG_EVICTED)) return INVALIDATE_ALREADY_INVALID;
      long newValue = State.setFlag(oldValue, State.FLAG_EVICTED | State.FLAG_MEM_RELEASED);
      if (state.compareAndSet(oldValue, newValue)) break;
    }
    if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
      LlapIoImpl.LOCKING_LOGGER.trace("Invalidated {} due to eviction", this);
    }
    return INVALIDATE_OK;
  }

};