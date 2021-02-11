//,temp,BackupSystemTable.java,1745,1752,temp,BackupSystemTable.java,1003,1011
//,3
public class xxx {
  public void finishDeleteOperation() throws IOException {
    LOG.trace("Finsih delete operation for backup ids");

    Delete delete = createDeleteForBackupDeleteOperation();
    try (Table table = connection.getTable(tableName)) {
      table.delete(delete);
    }
  }

};