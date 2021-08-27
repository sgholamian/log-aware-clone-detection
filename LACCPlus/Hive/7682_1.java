//,temp,LlapAllocatorBuffer.java,205,221,temp,LlapAllocatorBuffer.java,175,199
//,3
public class xxx {
  public int releaseInvalidated() {
    long oldValue, newValue;
    do {
      oldValue = state.get();
      if (!State.hasFlags(oldValue, State.FLAG_EVICTED)) {
        throw new AssertionError("Not invalidated");
      }
      if (State.hasFlags(oldValue, State.FLAG_MOVING | State.FLAG_REMOVED)) return -1;
      // No move pending and no intervening discard, the allocator can release.
      newValue = State.setFlag(oldValue, State.FLAG_REMOVED);
    } while (!state.compareAndSet(oldValue, newValue));
    if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
      LlapIoImpl.LOCKING_LOGGER.trace("Removed {}", this);
    }
    // Arena cannot change after we have marked it as released.
    return State.getArena(oldValue);
  }

};