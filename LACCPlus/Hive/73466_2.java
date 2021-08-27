//,temp,CachedStore.java,939,967,temp,CachedStore.java,870,897
//,3
public class xxx {
    private void updateTableColStats(RawStore rawStore, String catName, String dbName, String tblName) {
      LOG.debug("CachedStore: updating cached table col stats objects for catalog: {}, database: {}", catName, dbName);
      boolean committed = false;
      rawStore.openTransaction();
      try {
        Table table = rawStore.getTable(catName, dbName, tblName);
        if (table != null && !table.isSetPartitionKeys()) {
          List<String> colNames = MetaStoreUtils.getColumnNamesForTable(table);
          Deadline.startTimer("getTableColumnStatistics");
          ColumnStatistics tableColStats = rawStore.getTableColumnStatistics(catName, dbName, tblName, colNames, CacheUtils.HIVE_ENGINE);
          Deadline.stopTimer();
          if (tableColStats != null) {
            sharedCache.refreshTableColStatsInCache(StringUtils.normalizeIdentifier(catName),
                StringUtils.normalizeIdentifier(dbName), StringUtils.normalizeIdentifier(tblName),
                tableColStats.getStatsObj());
          }
        }
        committed = rawStore.commitTransaction();
        LOG.debug("CachedStore: updated cached table col stats objects for catalog: {}, database: {}", catName, dbName);
      } catch (MetaException | NoSuchObjectException e) {
        LOG.info("Unable to refresh table column stats for table: " + tblName, e);
      } finally {
        if (!committed) {
          sharedCache.removeAllTableColStatsFromCache(catName, dbName, tblName);
          rawStore.rollbackTransaction();
        }
      }
    }

};