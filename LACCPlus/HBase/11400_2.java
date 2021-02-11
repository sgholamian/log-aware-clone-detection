//,temp,TestFullRestore.java,242,263,temp,TestFullRestore.java,198,216
//,3
public class xxx {
  @Test
  public void testFullRestoreSingleOverwriteCommand() throws Exception {
    LOG.info("test full restore on a single table empty table: command-line");
    List<TableName> tables = Lists.newArrayList(table1);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));
    LOG.info("backup complete");
    TableName[] tableset = new TableName[] { table1 };
    // restore <backup_root_path> <backup_id> <tables> [tableMapping]
    String[] args =
        new String[] { BACKUP_ROOT_DIR, backupId, "-t", StringUtils.join(tableset, ","), "-o" };
    // Run restore
    int ret = ToolRunner.run(conf1, new RestoreDriver(), args);
    assertTrue(ret == 0);

    HBaseAdmin hba = TEST_UTIL.getHBaseAdmin();
    assertTrue(hba.tableExists(table1));
    hba.close();
  }

};