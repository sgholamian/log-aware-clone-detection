//,temp,TestFullRestore.java,242,263,temp,TestFullRestore.java,97,115
//,3
public class xxx {
  @Test
  public void testFullRestoreCheckCommand() throws Exception {
    LOG.info("test full restore on a single table: command-line, check only");

    List<TableName> tables = Lists.newArrayList(table1);
    String backupId = fullTableBackup(tables);
    LOG.info("backup complete");
    assertTrue(checkSucceeded(backupId));
    // restore <backup_root_path> <backup_id> <tables> [tableMapping]
    String[] args =
        new String[] { BACKUP_ROOT_DIR, backupId, "-t", table1.getNameAsString(), "-m",
            table1_restore.getNameAsString(), "-c" };
    // Run backup
    int ret = ToolRunner.run(conf1, new RestoreDriver(), args);
    assertTrue(ret == 0);
    //Verify that table has not been restored
    HBaseAdmin hba = TEST_UTIL.getHBaseAdmin();
    assertFalse(hba.tableExists(table1_restore));
  }

};