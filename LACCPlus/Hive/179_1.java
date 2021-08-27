//,temp,SharedCache.java,674,688,temp,QueryTracker.java,258,272
//,3
public class xxx {
    public Partition getPartition(List<String> partVals, SharedCache sharedCache) {
      Partition part = null;
      try {
        tableLock.readLock().lock();
        PartitionWrapper wrapper = partitionCache.get(CacheUtils.buildPartitionCacheKey(partVals));
        if (wrapper == null) {
          LOG.debug("Partition: " + partVals + " is not present in the cache.");
          return null;
        }
        part = CacheUtils.assemble(wrapper, sharedCache);
      } finally {
        tableLock.readLock().unlock();
      }
      return part;
    }

};