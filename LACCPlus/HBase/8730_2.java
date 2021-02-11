//,temp,TestRestoreBoundaryTests.java,48,61,temp,TestFullRestore.java,122,140
//,3
public class xxx {
  @Test
  public void testFullRestoreMultiple() throws Exception {
    LOG.info("create full backup image on multiple tables");
    List<TableName> tables = Lists.newArrayList(table2, table3);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));

    TableName[] restore_tableset = new TableName[] { table2, table3 };
    TableName[] tablemap = new TableName[] { table2_restore, table3_restore };
    BackupAdmin client = getBackupAdmin();
    client.restore(BackupUtils.createRestoreRequest(BACKUP_ROOT_DIR, backupId, false,
      restore_tableset, tablemap, false));
    HBaseAdmin hba = TEST_UTIL.getHBaseAdmin();
    assertTrue(hba.tableExists(table2_restore));
    assertTrue(hba.tableExists(table3_restore));
    TEST_UTIL.deleteTable(table2_restore);
    TEST_UTIL.deleteTable(table3_restore);
    hba.close();
  }

};