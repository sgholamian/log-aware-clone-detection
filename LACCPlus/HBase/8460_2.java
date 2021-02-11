//,temp,TableSpaceQuotaSnapshotNotifier.java,37,47,temp,BackupSystemTable.java,1735,1743
//,3
public class xxx {
  public void startDeleteOperation(String[] backupIdList) throws IOException {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Start delete operation for backups: " + StringUtils.join(backupIdList));
    }
    Put put = createPutForDeleteOperation(backupIdList);
    try (Table table = connection.getTable(tableName)) {
      table.put(put);
    }
  }

};