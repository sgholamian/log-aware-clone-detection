//,temp,TableSpaceQuotaSnapshotNotifier.java,37,47,temp,BackupSystemTable.java,1735,1743
//,3
public class xxx {
  @Override
  public void transitionTable(
      TableName tableName, SpaceQuotaSnapshot snapshot) throws IOException {
    final Put p = QuotaTableUtil.createPutForSpaceSnapshot(tableName, snapshot);
    try (Table quotaTable = conn.getTable(QuotaTableUtil.QUOTA_TABLE_NAME)) {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Persisting a space quota snapshot " + snapshot + " for " + tableName);
      }
      quotaTable.put(p);
    }
  }

};