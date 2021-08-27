//,temp,SharedCache.java,2082,2095,temp,SharedCache.java,2054,2066
//,3
public class xxx {
  public void updateTableColStatsInCache(String catName, String dbName, String tableName,
      List<ColumnStatisticsObj> colStatsForTable) {
    try {
      cacheLock.readLock().lock();
      TableWrapper tblWrapper = tableCache.getIfPresent(CacheUtils.buildTableKey(catName, dbName, tableName));
      if (tblWrapper != null) {
        tblWrapper.updateTableColStats(colStatsForTable);
      } else {
        LOG.info("Table " + tableName + " is missing from cache.");
      }
    } finally {
      cacheLock.readLock().unlock();
    }
  }

};