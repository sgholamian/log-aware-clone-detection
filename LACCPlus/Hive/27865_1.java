//,temp,ObjectStore.java,9319,9387,temp,ObjectStore.java,9233,9301
//,3
public class xxx {
  @Override
  public Map<String, String> updatePartitionColumnStatistics(ColumnStatistics colStats,
      List<String> partVals, String validWriteIds, long writeId)
          throws MetaException, NoSuchObjectException, InvalidObjectException, InvalidInputException {
    boolean committed = false;

    try {
      openTransaction();
      List<ColumnStatisticsObj> statsObjs = colStats.getStatsObj();
      ColumnStatisticsDesc statsDesc = colStats.getStatsDesc();
      String catName = statsDesc.isSetCatName() ? statsDesc.getCatName() : getDefaultCatalog(conf);
      Table table = ensureGetTable(catName, statsDesc.getDbName(), statsDesc.getTableName());
      Partition partition = convertToPart(getMPartition(
          catName, statsDesc.getDbName(), statsDesc.getTableName(), partVals), false);
      List<String> colNames = new ArrayList<>();

      for(ColumnStatisticsObj statsObj : statsObjs) {
        colNames.add(statsObj.getColName());
      }

      Map<String, MPartitionColumnStatistics> oldStats = getPartitionColStats(table, statsDesc
          .getPartName(), colNames, colStats.getEngine());

      MPartition mPartition = getMPartition(
          catName, statsDesc.getDbName(), statsDesc.getTableName(), partVals);
      if (partition == null) {
        throw new NoSuchObjectException("Partition for which stats is gathered doesn't exist.");
      }

      for (ColumnStatisticsObj statsObj : statsObjs) {
        MPartitionColumnStatistics mStatsObj =
            StatObjectConverter.convertToMPartitionColumnStatistics(mPartition, statsDesc, statsObj, colStats.getEngine());
        writeMPartitionColumnStatistics(table, partition, mStatsObj,
            oldStats.get(statsObj.getColName()));
      }
      // TODO: (HIVE-20109) the col stats stats should be in colstats, not in the partition!
      Map<String, String> newParams = new HashMap<>(mPartition.getParameters());
      StatsSetupConst.setColumnStatsState(newParams, colNames);
      boolean isTxn = TxnUtils.isTransactionalTable(table);
      if (isTxn) {
        if (!areTxnStatsSupported) {
          StatsSetupConst.setBasicStatsState(newParams, StatsSetupConst.FALSE);
        } else {
          String errorMsg = verifyStatsChangeCtx(TableName.getDbTable(statsDesc.getDbName(),
                                                                      statsDesc.getTableName()),
                  mPartition.getParameters(), newParams, writeId, validWriteIds, true);
          if (errorMsg != null) {
            throw new MetaException(errorMsg);
          }
          if (!isCurrentStatsValidForTheQuery(mPartition, validWriteIds, true)) {
            // Make sure we set the flag to invalid regardless of the current value.
            StatsSetupConst.setBasicStatsState(newParams, StatsSetupConst.FALSE);
            LOG.info("Removed COLUMN_STATS_ACCURATE from the parameters of the partition "
                    + statsDesc.getDbName() + "." + statsDesc.getTableName() + "." + statsDesc.getPartName());
          }
          mPartition.setWriteId(writeId);
        }
      }

      mPartition.setParameters(newParams);
      committed = commitTransaction();
      // TODO: what is the "return committed;" about? would it ever return false without throwing?
      return committed ? newParams : null;
    } finally {
      if (!committed) {
        rollbackTransaction();
      }
    }
  }

};