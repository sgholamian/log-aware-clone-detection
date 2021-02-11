//,temp,MetaCache.java,408,435,temp,MetaCache.java,320,346
//,3
public class xxx {
  public void clearCache(final TableName tableName, final byte [] row, int replicaId) {
    ConcurrentMap<byte[], RegionLocations> tableLocations = getTableLocations(tableName);

    RegionLocations regionLocations = getCachedLocation(tableName, row);
    if (regionLocations != null) {
      HRegionLocation toBeRemoved = regionLocations.getRegionLocation(replicaId);
      if (toBeRemoved != null) {
        RegionLocations updatedLocations = regionLocations.remove(replicaId);
        byte[] startKey = regionLocations.getRegionLocation().getRegion().getStartKey();
        boolean removed;
        if (updatedLocations.isEmpty()) {
          removed = tableLocations.remove(startKey, regionLocations);
        } else {
          removed = tableLocations.replace(startKey, regionLocations, updatedLocations);
        }

        if (removed) {
          if (metrics != null) {
            metrics.incrMetaCacheNumClearRegion();
          }
          if (LOG.isTraceEnabled()) {
            LOG.trace("Removed " + toBeRemoved + " from cache");
          }
        }
      }
    }
  }

};