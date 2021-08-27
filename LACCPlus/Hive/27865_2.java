//,temp,ObjectStore.java,9319,9387,temp,ObjectStore.java,9233,9301
//,3
public class xxx {
  @Override
  public Map<String, String> updateTableColumnStatistics(ColumnStatistics colStats,
      String validWriteIds, long writeId)
    throws NoSuchObjectException, MetaException, InvalidObjectException, InvalidInputException {
    boolean committed = false;

    openTransaction();
    try {
      List<ColumnStatisticsObj> statsObjs = colStats.getStatsObj();
      ColumnStatisticsDesc statsDesc = colStats.getStatsDesc();

      // DataNucleus objects get detached all over the place for no (real) reason.
      // So let's not use them anywhere unless absolutely necessary.
      String catName = statsDesc.isSetCatName() ? statsDesc.getCatName() : getDefaultCatalog(conf);
      Table table = ensureGetTable(catName, statsDesc.getDbName(), statsDesc.getTableName());
      List<String> colNames = new ArrayList<>();
      for (ColumnStatisticsObj statsObj : statsObjs) {
        colNames.add(statsObj.getColName());
      }

      Map<String, MTableColumnStatistics> oldStats = getPartitionColStats(table, colNames, colStats.getEngine());

      for (ColumnStatisticsObj statsObj:statsObjs) {
        // We have to get mtable again because DataNucleus.
        MTableColumnStatistics mStatsObj = StatObjectConverter.convertToMTableColumnStatistics(
            ensureGetMTable(catName, statsDesc.getDbName(), statsDesc.getTableName()), statsDesc,
            statsObj, colStats.getEngine());
        writeMTableColumnStatistics(table, mStatsObj, oldStats.get(statsObj.getColName()));
        // There is no need to add colname again, otherwise we will get duplicate colNames.
      }

      // TODO: (HIVE-20109) ideally the col stats stats should be in colstats, not in the table!
      // Set the table properties
      // No need to check again if it exists.
      String dbname = table.getDbName();
      String name = table.getTableName();
      MTable oldt = getMTable(catName, dbname, name);
      Map<String, String> newParams = new HashMap<>(table.getParameters());
      StatsSetupConst.setColumnStatsState(newParams, colNames);
      boolean isTxn = TxnUtils.isTransactionalTable(oldt.getParameters());
      if (isTxn) {
        if (!areTxnStatsSupported) {
          StatsSetupConst.setBasicStatsState(newParams, StatsSetupConst.FALSE);
        } else {
          String errorMsg = verifyStatsChangeCtx(TableName.getDbTable(dbname, name),
              oldt.getParameters(), newParams, writeId, validWriteIds, true);
          if (errorMsg != null) {
            throw new MetaException(errorMsg);
          }
          if (!isCurrentStatsValidForTheQuery(oldt, validWriteIds, true)) {
            // Make sure we set the flag to invalid regardless of the current value.
            StatsSetupConst.setBasicStatsState(newParams, StatsSetupConst.FALSE);
            LOG.info("Removed COLUMN_STATS_ACCURATE from the parameters of the table "
                + dbname + "." + name);
          }
          oldt.setWriteId(writeId);
        }
      }
      oldt.setParameters(newParams);

      committed = commitTransaction();
      // TODO: similar to update...Part, this used to do "return committed;"; makes little sense.
      return committed ? newParams : null;
    } finally {
      if (!committed) {
        rollbackTransaction();
      }
    }
  }

};