//,temp,TestRestoreBoundaryTests.java,48,61,temp,TestFullRestore.java,122,140
//,3
public class xxx {
  @Test
  public void testFullRestoreSingleEmpty() throws Exception {
    LOG.info("test full restore on a single table empty table");
    String backupId = fullTableBackup(toList(table1.getNameAsString()));
    LOG.info("backup complete");
    TableName[] tableset = new TableName[] { table1 };
    TableName[] tablemap = new TableName[] { table1_restore };
    getBackupAdmin().restore(
      BackupUtils.createRestoreRequest(BACKUP_ROOT_DIR, backupId, false, tableset, tablemap,
        false));
    HBaseAdmin hba = TEST_UTIL.getHBaseAdmin();
    assertTrue(hba.tableExists(table1_restore));
    TEST_UTIL.deleteTable(table1_restore);
  }

};