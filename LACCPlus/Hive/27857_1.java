//,temp,LlapAllocatorBuffer.java,350,375,temp,LlapAllocatorBuffer.java,320,344
//,3
public class xxx {
  public Boolean endDiscard() {
    long oldValue, newValue;
    Boolean result;
    do {
      oldValue = state.get();
      assert State.hasFlags(oldValue, State.FLAG_MOVING);
      newValue = State.switchFlag(oldValue, State.FLAG_MOVING);
      newValue = State.setFlag(newValue,
          State.FLAG_EVICTED | State.FLAG_MEM_RELEASED | State.FLAG_REMOVED);
      result = null;
      // See if someone else evicted this in parallel.
      if (State.hasFlags(oldValue, State.FLAG_EVICTED)) {
        if (State.hasFlags(oldValue, State.FLAG_REMOVED)) {
          throw new AssertionError("Removed during the move " + this);
        }
        result = !State.hasFlags(oldValue, State.FLAG_MEM_RELEASED);
      }
    } while (!state.compareAndSet(oldValue, newValue));
    if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
      LlapIoImpl.LOCKING_LOGGER.trace("Discared {}", this);
    }
    synchronized (state) {
      state.notifyAll();
    }
    return result;
  }

};