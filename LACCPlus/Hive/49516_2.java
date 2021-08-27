//,temp,ObjectStore.java,2718,2760,temp,ObjectStore.java,1591,1634
//,3
public class xxx {
  @Override
  public Table getTable(String catName, String dbName, String tableName, String writeIdList)
      throws MetaException {
    boolean commited = false;
    Table tbl = null;
    try {
      openTransaction();
      MTable mtable = getMTable(catName, dbName, tableName);
      tbl = convertToTable(mtable);
      // Retrieve creation metadata if needed
      if (tbl != null && TableType.MATERIALIZED_VIEW.toString().equals(tbl.getTableType())) {
        tbl.setCreationMetadata(
                convertToCreationMetadata(getCreationMetadata(catName, dbName, tableName)));
      }

      // If transactional non partitioned table,
      // check whether the current version table statistics
      // in the metastore comply with the client query's snapshot isolation.
      // Note: a partitioned table has table stats and table snapshot in MPartiiton.
      if (writeIdList != null) {
        boolean isTxn = TxnUtils.isTransactionalTable(tbl);
        if (isTxn && !areTxnStatsSupported) {
          StatsSetupConst.setBasicStatsState(tbl.getParameters(), StatsSetupConst.FALSE);
          LOG.info("Removed COLUMN_STATS_ACCURATE from Table's parameters.");
        } else if (isTxn && tbl.getPartitionKeysSize() == 0) {
          if (isCurrentStatsValidForTheQuery(mtable, writeIdList, false)) {
            tbl.setIsStatsCompliant(true);
          } else {
            tbl.setIsStatsCompliant(false);
            // Do not make persistent the following state since it is the query specific (not global).
            StatsSetupConst.setBasicStatsState(tbl.getParameters(), StatsSetupConst.FALSE);
            LOG.info("Removed COLUMN_STATS_ACCURATE from Table's parameters.");
          }
        }
      }
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }

    return tbl;
  }

};