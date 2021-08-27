//,temp,SharedCache.java,2082,2095,temp,SharedCache.java,2054,2066
//,3
public class xxx {
  public void removeTableColStatsFromCache(String catName, String dbName, String tblName, String colName) {
    try {
      cacheLock.readLock().lock();
      TableWrapper tblWrapper = tableCache.getIfPresent(CacheUtils.buildTableKey(catName, dbName, tblName));
      if (tblWrapper != null) {
        tblWrapper.removeTableColStats(colName);
      } else {
        LOG.info("Table " + tblName + " is missing from cache.");
      }
    } finally {
      cacheLock.readLock().unlock();
    }
  }

};