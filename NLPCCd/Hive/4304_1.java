//,temp,sample_2214.java,2,15,temp,sample_2216.java,2,17
//,3
public class xxx {
public int invalidate() {
while (true) {
long oldValue = state.get();
if (State.getRefCount(oldValue) != 0) return INVALIDATE_FAILED;
if (State.hasFlags(oldValue, State.FLAG_EVICTED)) return INVALIDATE_ALREADY_INVALID;
long newValue = State.setFlag(oldValue, State.FLAG_EVICTED | State.FLAG_MEM_RELEASED);
if (state.compareAndSet(oldValue, newValue)) break;
}
if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {


log.info("invalidated due to eviction");
}
}

};