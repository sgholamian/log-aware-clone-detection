//,temp,SharedCache.java,1023,1044,temp,SharedCache.java,967,994
//,3
public class xxx {
    public void refreshTableColStats(List<ColumnStatisticsObj> colStatsForTable) {
      Map<String, ColumnStatisticsObj> newTableColStatsCache = new HashMap<String, ColumnStatisticsObj>();
      try {
        tableLock.writeLock().lock();
        int statsSize = 0;
        for (ColumnStatisticsObj colStatObj : colStatsForTable) {
          if (compareAndSetMemberCacheUpdated(MemberName.TABLE_COL_STATS_CACHE,true, false)) {
            LOG.debug("Skipping table col stats cache update for table: " + getTable().getTableName()
                + "; the table col stats list we have is dirty.");
            return;
          }
          String key = colStatObj.getColName();
          // TODO: get rid of deepCopy after making sure callers don't use references
          newTableColStatsCache.put(key, colStatObj.deepCopy());
          statsSize += getObjectSize(ColumnStatisticsObj.class, colStatObj);
        }
        tableColStatsCache = newTableColStatsCache;
        updateMemberSize(MemberName.TABLE_COL_STATS_CACHE, statsSize, SizeMode.Snapshot);
      } finally {
        tableLock.writeLock().unlock();
      }
    }

};