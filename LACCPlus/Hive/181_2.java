//,temp,SharedCache.java,2097,2110,temp,SharedCache.java,2068,2080
//,3
public class xxx {
  public void removeAllTableColStatsFromCache(String catName, String dbName, String tblName) {
    try {
      cacheLock.readLock().lock();
      TableWrapper tblWrapper = tableCache.getIfPresent(CacheUtils.buildTableKey(catName, dbName, tblName));
      if (tblWrapper != null) {
        tblWrapper.removeAllTableColStats();
      } else {
        LOG.info("Table " + tblName + " is missing from cache.");
      }
    } finally {
      cacheLock.readLock().unlock();
    }
  }

};