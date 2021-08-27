//,temp,CachedStore.java,939,967,temp,CachedStore.java,870,897
//,3
public class xxx {
    private void updateTablePartitionColStats(RawStore rawStore, String catName, String dbName, String tblName) {
      LOG.debug("CachedStore: updating cached partition col stats objects for catalog: {}, database: {}, table: {}",
          catName, dbName, tblName);
      boolean committed = false;
      rawStore.openTransaction();
      try {
        Table table = rawStore.getTable(catName, dbName, tblName);
        if (table != null) {
          List<String> colNames = MetaStoreUtils.getColumnNamesForTable(table);
          List<String> partNames = rawStore.listPartitionNames(catName, dbName, tblName, (short) -1);
          // Get partition column stats for this table
          Deadline.startTimer("getPartitionColumnStatistics");
          List<ColumnStatistics> partitionColStats =
              rawStore.getPartitionColumnStatistics(catName, dbName, tblName, partNames, colNames, CacheUtils.HIVE_ENGINE);
          Deadline.stopTimer();
          sharedCache.refreshPartitionColStatsInCache(catName, dbName, tblName, partitionColStats);
        }
        committed = rawStore.commitTransaction();
        LOG.debug("CachedStore: updated cached partition col stats objects for catalog: {}, database: {}, table: {}",
            catName, dbName, tblName);
      } catch (MetaException | NoSuchObjectException e) {
        LOG.info("Updating CachedStore: unable to read partitions of table: " + tblName, e);
      } finally {
        if (!committed) {
          sharedCache.removeAllPartitionColStatsFromCache(catName, dbName, tblName);
          rawStore.rollbackTransaction();
        }
      }
    }

};