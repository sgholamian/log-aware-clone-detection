//,temp,RegionSizeStoreImpl.java,70,78,temp,RegionSizeStoreImpl.java,60,68
//,2
public class xxx {
  @Override
  public void incrementRegionSize(RegionInfo regionInfo, long delta) {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Updating space quota size for " + regionInfo + " with a delta of " + delta);
    }
    // Atomic. Recomputes the stored value with the delta if there is one, otherwise use the delta.
    store.compute(regionInfo,
      (key,value) -> value == null ? new RegionSizeImpl(delta) : value.incrementSize(delta));
  }

};