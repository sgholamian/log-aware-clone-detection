//,temp,SharedCache.java,2596,2611,temp,SharedCache.java,2315,2329
//,3
public class xxx {
  public List<ColumnStatistics> getPartitionColStatsListFromCache(String catName, String dbName, String tblName,
      List<String> partNames, List<String> colNames, String writeIdList, boolean txnStatSupported) {
    List<ColumnStatistics> colStatObjs = null;
    try {
      cacheLock.readLock().lock();
      TableWrapper tblWrapper = tableCache.getIfPresent(CacheUtils.buildTableKey(catName, dbName, tblName));
      if (tblWrapper != null) {
        colStatObjs = tblWrapper.getPartColStatsList(partNames, colNames, writeIdList, txnStatSupported);
      }
    } catch (MetaException e) {
      LOG.warn("Failed to get partition column statistics");
    } finally {
      cacheLock.readLock().unlock();
    }
    return colStatObjs;
  }

};