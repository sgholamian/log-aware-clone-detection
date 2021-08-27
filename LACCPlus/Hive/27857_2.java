//,temp,LlapAllocatorBuffer.java,350,375,temp,LlapAllocatorBuffer.java,320,344
//,3
public class xxx {
  public Boolean cancelDiscard() {
    long oldValue, newValue;
    Boolean result;
    do {
      oldValue = state.get();
      assert State.hasFlags(oldValue, State.FLAG_MOVING) : this.toDebugString();
      newValue = State.switchFlag(oldValue, State.FLAG_MOVING);
      result = null;
      if (State.hasFlags(oldValue, State.FLAG_EVICTED)) {
        if (State.hasFlags(oldValue, State.FLAG_REMOVED)) {
          throw new AssertionError("Removed during the move " + this);
        }
        result = !State.hasFlags(oldValue, State.FLAG_MEM_RELEASED);
        // Not necessary here cause noone will be looking at these after us; set them for clarity.
        newValue = State.setFlag(newValue, State.FLAG_MEM_RELEASED | State.FLAG_REMOVED);
      }
    } while (!state.compareAndSet(oldValue, newValue));
    if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
      LlapIoImpl.LOCKING_LOGGER.trace("Move ended for {}", this);
    }
    synchronized (state) {
      state.notifyAll();
    }
    return result;
  }

};