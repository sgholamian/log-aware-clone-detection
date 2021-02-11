//,temp,TestFullRestore.java,315,329,temp,TestFullRestore.java,178,191
//,3
public class xxx {
  @Test
  public void testFullRestoreSingleOverwrite() throws Exception {
    LOG.info("test full restore on a single table empty table");
    List<TableName> tables = Lists.newArrayList(table1);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));

    LOG.info("backup complete");

    TableName[] tableset = new TableName[] { table1 };
    BackupAdmin client = getBackupAdmin();
    client.restore(BackupUtils.createRestoreRequest(BACKUP_ROOT_DIR, backupId, false,
      tableset, null, true));
  }

};