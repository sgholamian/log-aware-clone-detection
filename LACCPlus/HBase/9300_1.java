//,temp,TestFullRestore.java,336,353,temp,TestFullRestore.java,291,308
//,3
public class xxx {
  @Test
  public void testFullRestoreMultipleDNECommand() throws Exception {
    LOG.info("test restore fails on multiple tables that do not exist: command-line");

    List<TableName> tables = Lists.newArrayList(table2, table3);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));

    TableName[] restore_tableset =
        new TableName[] { TableName.valueOf("faketable1"), TableName.valueOf("faketable2") };
    TableName[] tablemap = new TableName[] { table2_restore, table3_restore };
    String[] args =
        new String[] { BACKUP_ROOT_DIR, backupId, StringUtils.join(restore_tableset, ","), "-m",
            StringUtils.join(tablemap, ",") };
    // Run restore
    int ret = ToolRunner.run(conf1, new RestoreDriver(), args);
    assertTrue(ret != 0);
  }

};