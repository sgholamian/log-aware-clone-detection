//,temp,SharedCache.java,2097,2110,temp,SharedCache.java,2068,2080
//,3
public class xxx {
  public void refreshTableColStatsInCache(String catName, String dbName, String tableName,
      List<ColumnStatisticsObj> colStatsForTable) {
    try {
      cacheLock.readLock().lock();
      TableWrapper tblWrapper = tableCache.getIfPresent(CacheUtils.buildTableKey(catName, dbName, tableName));
      if (tblWrapper != null) {
        tblWrapper.refreshTableColStats(colStatsForTable);
      } else {
        LOG.info("Table " + tableName + " is missing from cache.");
      }
    } finally {
      cacheLock.readLock().unlock();
    }
  }

};