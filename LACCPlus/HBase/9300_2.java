//,temp,TestFullRestore.java,336,353,temp,TestFullRestore.java,291,308
//,3
public class xxx {
  @Test
  public void testFullRestoreSingleDNECommand() throws Exception {
    LOG.info("test restore fails on a single table that does not exist: command-line");
    List<TableName> tables = Lists.newArrayList(table1);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));

    LOG.info("backup complete");

    TableName[] tableset = new TableName[] { TableName.valueOf("faketable") };
    TableName[] tablemap = new TableName[] { table1_restore };
    String[] args =
        new String[] { BACKUP_ROOT_DIR, backupId, StringUtils.join(tableset, ","), "-m",
            StringUtils.join(tablemap, ",") };
    // Run restore
    int ret = ToolRunner.run(conf1, new RestoreDriver(), args);
    assertTrue(ret != 0);
  }

};