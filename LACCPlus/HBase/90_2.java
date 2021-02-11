//,temp,BackupSystemTable.java,1816,1824,temp,BackupSystemTable.java,1806,1814
//,3
public class xxx {
  public void startMergeOperation(String[] backupIdList) throws IOException {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Start merge operation for backups: " + StringUtils.join(backupIdList));
    }
    Put put = createPutForMergeOperation(backupIdList);
    try (Table table = connection.getTable(tableName)) {
      table.put(put);
    }
  }

};