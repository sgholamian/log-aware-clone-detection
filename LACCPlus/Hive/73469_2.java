//,temp,HMSHandler.java,9080,9132,temp,HMSHandler.java,9002,9077
//,3
public class xxx {
  private boolean updatePartColumnStatsWithMerge(String catName, String dbName, String tableName,
                                                 List<String> colNames, Map<String, ColumnStatistics> newStatsMap, SetPartitionsStatsRequest request)
      throws MetaException, NoSuchObjectException, InvalidObjectException, InvalidInputException {
    RawStore ms = getMS();
    ms.openTransaction();
    boolean isCommitted = false, result = false;
    try {
      // a single call to get all column stats for all partitions
      List<String> partitionNames = new ArrayList<>();
      partitionNames.addAll(newStatsMap.keySet());
      List<ColumnStatistics> csOlds = ms.getPartitionColumnStatistics(catName, dbName, tableName,
          partitionNames, colNames, request.getEngine(), request.getValidWriteIdList());
      if (newStatsMap.values().size() != csOlds.size()) {
        // some of the partitions miss stats.
        LOG.debug("Some of the partitions miss stats.");
      }
      Map<String, ColumnStatistics> oldStatsMap = new HashMap<>();
      for (ColumnStatistics csOld : csOlds) {
        oldStatsMap.put(csOld.getStatsDesc().getPartName(), csOld);
      }

      // another single call to get all the partition objects
      List<Partition> partitions = ms.getPartitionsByNames(catName, dbName, tableName, partitionNames);
      Map<String, Partition> mapToPart = new HashMap<>();
      for (int index = 0; index < partitionNames.size(); index++) {
        mapToPart.put(partitionNames.get(index), partitions.get(index));
      }

      Table t = getTable(catName, dbName, tableName);
      for (Map.Entry<String, ColumnStatistics> entry : newStatsMap.entrySet()) {
        ColumnStatistics csNew = entry.getValue();
        ColumnStatistics csOld = oldStatsMap.get(entry.getKey());
        boolean isInvalidTxnStats = csOld != null
            && csOld.isSetIsStatsCompliant() && !csOld.isIsStatsCompliant();
        Partition part = mapToPart.get(entry.getKey());
        if (isInvalidTxnStats) {
          // No columns can be merged; a shortcut for getMergableCols.
          csNew.setStatsObj(Lists.newArrayList());
        } else {
          // we first use getParameters() to prune the stats
          MetaStoreServerUtils.getMergableCols(csNew, part.getParameters());
          // we merge those that can be merged
          if (csOld != null && csOld.getStatsObjSize() != 0 && !csNew.getStatsObj().isEmpty()) {
            MetaStoreServerUtils.mergeColStats(csNew, csOld);
          }
        }

        if (!csNew.getStatsObj().isEmpty()) {
          // We don't short-circuit on errors here anymore. That can leave acid stats invalid.
          result = updatePartitonColStatsInternal(t, csNew,
              request.getValidWriteIdList(), request.getWriteId()) && result;
        } else if (isInvalidTxnStats) {
          // For now because the stats state is such as it is, we will invalidate everything.
          // Overall the sematics here are not clear - we could invalide only some columns, but does
          // that make any physical sense? Could query affect some columns but not others?
          part.setWriteId(request.getWriteId());
          StatsSetupConst.clearColumnStatsState(part.getParameters());
          StatsSetupConst.setBasicStatsState(part.getParameters(), StatsSetupConst.FALSE);
          ms.alterPartition(catName, dbName, tableName, part.getValues(), part,
              request.getValidWriteIdList());
          result = false;
        } else {
          // TODO: why doesn't the original call for non acid tables invalidate the stats?
          LOG.debug("All the column stats " + csNew.getStatsDesc().getPartName()
              + " are not accurate to merge.");
        }
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