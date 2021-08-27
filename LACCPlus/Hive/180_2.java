//,temp,SharedCache.java,2596,2611,temp,SharedCache.java,2315,2329
//,3
public class xxx {
  public Partition removePartitionFromCache(String catName, String dbName, String tblName, List<String> partVals) {
    Partition part = null;
    try {
      cacheLock.readLock().lock();
      TableWrapper tblWrapper = tableCache.getIfPresent(CacheUtils.buildTableKey(catName, dbName, tblName));
      if (tblWrapper != null) {
        part = tblWrapper.removePartition(partVals, this);
      } else {
        LOG.warn("This is abnormal");
      }
    } finally {
      cacheLock.readLock().unlock();
    }
    return part;
  }

};