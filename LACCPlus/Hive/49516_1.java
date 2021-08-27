//,temp,ObjectStore.java,2718,2760,temp,ObjectStore.java,1591,1634
//,3
public class xxx {
  @Override
  public Partition getPartition(String catName, String dbName, String tableName,
                                List<String> part_vals,
                                String validWriteIds)
      throws NoSuchObjectException, MetaException {
    Partition part = null;
    boolean committed = false;
    try {
      openTransaction();
      MTable table = this.getMTable(catName, dbName, tableName);
      MPartition mpart = getMPartition(catName, dbName, tableName, part_vals);
      part = convertToPart(mpart, false);
      committed = commitTransaction();
      if (part == null) {
        throw new NoSuchObjectException("partition values="
          + part_vals.toString());
      }

      part.setValues(part_vals);
      // If transactional table partition, check whether the current version partition
      // statistics in the metastore comply with the client query's snapshot isolation.
      long statsWriteId = mpart.getWriteId();
      if (TxnUtils.isTransactionalTable(table.getParameters())) {
        if (!areTxnStatsSupported) {
          // Do not make persistent the following state since it is query specific (not global).
          StatsSetupConst.setBasicStatsState(part.getParameters(), StatsSetupConst.FALSE);
          LOG.info("Removed COLUMN_STATS_ACCURATE from Partition object's parameters.");
        } else if (validWriteIds != null) {
          if (isCurrentStatsValidForTheQuery(part, statsWriteId, validWriteIds, false)) {
            part.setIsStatsCompliant(true);
          } else {
            part.setIsStatsCompliant(false);
            // Do not make persistent the following state since it is query specific (not global).
            StatsSetupConst.setBasicStatsState(part.getParameters(), StatsSetupConst.FALSE);
            LOG.info("Removed COLUMN_STATS_ACCURATE from Partition object's parameters.");
          }
        }
      }
    } finally {
      rollbackAndCleanup(committed, (Query)null);
    }
    return part;
  }

};