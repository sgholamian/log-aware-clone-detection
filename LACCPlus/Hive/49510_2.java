//,temp,LlapAllocatorBuffer.java,154,167,temp,LlapAllocatorBuffer.java,130,146
//,3
public class xxx {
  public int decRef() {
    long newState, oldState;
    do {
      oldState = state.get();
      // We have to check it here since invalid decref will overflow.
      int oldRefCount = State.getRefCount(oldState);
      if (oldRefCount == 0) {
        throw new AssertionError("Invalid decRef when refCount is 0: " + this);
      }
      newState = State.decRefCount(oldState);
    } while (!state.compareAndSet(oldState, newState));
    int newRefCount = State.getRefCount(newState);
    if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
      LlapIoImpl.LOCKING_LOGGER.trace("Unlocked {}; refcount {}", this, newRefCount);
    }
    return newRefCount;
  }

};