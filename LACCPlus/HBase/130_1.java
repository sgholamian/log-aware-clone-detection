//,temp,TestFullRestore.java,315,329,temp,TestFullRestore.java,178,191
//,3
public class xxx {
  @Test(expected = IOException.class)
  public void testFullRestoreMultipleDNE() throws Exception {
    LOG.info("test restore fails on multiple tables that do not exist");

    List<TableName> tables = Lists.newArrayList(table2, table3);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));

    TableName[] restore_tableset =
        new TableName[] { TableName.valueOf("faketable1"), TableName.valueOf("faketable2") };
    TableName[] tablemap = new TableName[] { table2_restore, table3_restore };
    BackupAdmin client = getBackupAdmin();
    client.restore(BackupUtils.createRestoreRequest(BACKUP_ROOT_DIR, backupId, false,
      restore_tableset, tablemap, false));
  }

};