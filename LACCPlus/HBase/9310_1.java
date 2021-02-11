//,temp,MetaCache.java,408,435,temp,MetaCache.java,320,346
//,3
public class xxx {
  public void clearCache(final HRegionLocation location) {
    if (location == null) {
      return;
    }
    TableName tableName = location.getRegion().getTable();
    ConcurrentMap<byte[], RegionLocations> tableLocations = getTableLocations(tableName);
    RegionLocations regionLocations = tableLocations.get(location.getRegion().getStartKey());
    if (regionLocations != null) {
      RegionLocations updatedLocations = regionLocations.remove(location);
      boolean removed;
      if (updatedLocations != regionLocations) {
        if (updatedLocations.isEmpty()) {
          removed = tableLocations.remove(location.getRegion().getStartKey(), regionLocations);
        } else {
          removed = tableLocations.replace(location.getRegion().getStartKey(), regionLocations,
              updatedLocations);
        }
        if (removed) {
          if (metrics != null) {
            metrics.incrMetaCacheNumClearRegion();
          }
          if (LOG.isTraceEnabled()) {
            LOG.trace("Removed " + location + " from cache");
          }
        }
      }
    }
  }

};