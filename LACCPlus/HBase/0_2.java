//,temp,BackupSystemTable.java,1745,1752,temp,BackupSystemTable.java,1003,1011
//,3
public class xxx {
  public void deleteIncrementalBackupTableSet(String backupRoot) throws IOException {
    if (LOG.isTraceEnabled()) {
      LOG.trace("Delete incremental backup table set to backup system table. ROOT=" + backupRoot);
    }
    try (Table table = connection.getTable(tableName)) {
      Delete delete = createDeleteForIncrBackupTableSet(backupRoot);
      table.delete(delete);
    }
  }

};