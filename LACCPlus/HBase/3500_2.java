//,temp,RegionSizeStoreImpl.java,70,78,temp,RegionSizeStoreImpl.java,60,68
//,2
public class xxx {
  @Override
  public void put(RegionInfo regionInfo, long size) {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Setting space quota size for " + regionInfo + " to " + size);
    }
    // Atomic. Either sets the new size for the first time, or replaces the existing value.
    store.compute(regionInfo,
      (key,value) -> value == null ? new RegionSizeImpl(size) : value.setSize(size));
  }

};