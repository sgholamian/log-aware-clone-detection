//,temp,sample_2214.java,2,15,temp,sample_2216.java,2,17
//,3
public class xxx {
public void dummy_method(){
long oldValue, newValue;
do {
oldValue = state.get();
if (!State.hasFlags(oldValue, State.FLAG_EVICTED)) {
throw new AssertionError("Not invalidated");
}
if (State.hasFlags(oldValue, State.FLAG_MOVING | State.FLAG_REMOVED)) return -1;
newValue = State.setFlag(oldValue, State.FLAG_REMOVED);
} while (!state.compareAndSet(oldValue, newValue));
if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {


log.info("removed");
}
}

};