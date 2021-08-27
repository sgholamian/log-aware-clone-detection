//,temp,SharedCache.java,1023,1044,temp,SharedCache.java,967,994
//,3
public class xxx {
    public void refreshPartitions(List<Partition> partitions, SharedCache sharedCache) {
      Map<String, PartitionWrapper> newPartitionCache = new HashMap<String, PartitionWrapper>();
      try {
        tableLock.writeLock().lock();
        int size = 0;
        for (Partition part : partitions) {
          if (compareAndSetMemberCacheUpdated(MemberName.PARTITION_CACHE, true, false)) {
            LOG.debug("Skipping partition cache update for table: " + getTable().getTableName()
                + "; the partition list we have is dirty.");
            return;
          }
          String key = CacheUtils.buildPartitionCacheKey(part.getValues());
          PartitionWrapper wrapper = partitionCache.get(key);
          if (wrapper != null) {
            if (wrapper.getSdHash() != null) {
              sharedCache.decrSd(wrapper.getSdHash());
            }
          }
          wrapper = makePartitionWrapper(part, sharedCache);
          newPartitionCache.put(key, wrapper);
          size += getObjectSize(PartitionWrapper.class, wrapper);
        }
        partitionCache = newPartitionCache;
        updateMemberSize(MemberName.PARTITION_CACHE, size, SizeMode.Snapshot);
      } finally {
        tableLock.writeLock().unlock();
      }
    }

};