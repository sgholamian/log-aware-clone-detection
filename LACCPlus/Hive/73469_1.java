//,temp,HMSHandler.java,9080,9132,temp,HMSHandler.java,9002,9077
//,3
public class xxx {
  private boolean updateTableColumnStatsWithMerge(String catName, String dbName, String tableName,
                                                  List<String> colNames, SetPartitionsStatsRequest request) throws MetaException,
      NoSuchObjectException, InvalidObjectException, InvalidInputException {
    ColumnStatistics firstColStats = request.getColStats().get(0);
    RawStore ms = getMS();
    ms.openTransaction();
    boolean isCommitted = false, result = false;
    try {
      ColumnStatistics csOld = ms.getTableColumnStatistics(catName, dbName, tableName, colNames,
          request.getEngine(), request.getValidWriteIdList());
      // we first use the valid stats list to prune the stats
      boolean isInvalidTxnStats = csOld != null
          && csOld.isSetIsStatsCompliant() && !csOld.isIsStatsCompliant();
      if (isInvalidTxnStats) {
        // No columns can be merged; a shortcut for getMergableCols.
        firstColStats.setStatsObj(Lists.newArrayList());
      } else {
        Table t = getTable(catName, dbName, tableName);
        MetaStoreServerUtils.getMergableCols(firstColStats, t.getParameters());

        // we merge those that can be merged
        if (csOld != null && csOld.getStatsObjSize() != 0 && !firstColStats.getStatsObj().isEmpty()) {
          MetaStoreServerUtils.mergeColStats(firstColStats, csOld);
        }
      }

      if (!firstColStats.getStatsObj().isEmpty()) {
        result = updateTableColumnStatsInternal(firstColStats,
            request.getValidWriteIdList(), request.getWriteId());
      } else if (isInvalidTxnStats) {
        // For now because the stats state is such as it is, we will invalidate everything.
        // Overall the sematics here are not clear - we could invalide only some columns, but does
        // that make any physical sense? Could query affect some columns but not others?
        Table t = getTable(catName, dbName, tableName);
        t.setWriteId(request.getWriteId());
        StatsSetupConst.clearColumnStatsState(t.getParameters());
        StatsSetupConst.setBasicStatsState(t.getParameters(), StatsSetupConst.FALSE);
        ms.alterTable(catName, dbName, tableName, t, request.getValidWriteIdList());
      } else {
        // TODO: why doesn't the original call for non acid tables invalidate the stats?
        LOG.debug("All the column stats are not accurate to merge.");
        result = true;
      }

      ms.commitTransaction();
      isCommitted = true;
    } finally {
      if (!isCommitted) {
        ms.rollbackTransaction();
      }
    }
    return result;
  }

};