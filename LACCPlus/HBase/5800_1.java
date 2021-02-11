//,temp,TestFullRestore.java,242,263,temp,TestFullRestore.java,97,115
//,3
public class xxx {
  @Test
  public void testFullRestoreMultipleOverwriteCommand() throws Exception {
    LOG.info("create full backup image on multiple tables: command-line");

    List<TableName> tables = Lists.newArrayList(table2, table3);
    String backupId = fullTableBackup(tables);
    assertTrue(checkSucceeded(backupId));

    TableName[] restore_tableset = new TableName[] { table2, table3 };
    // restore <backup_root_path> <backup_id> <tables> [tableMapping]
    String[] args =
        new String[] { BACKUP_ROOT_DIR, backupId, "-t",
        StringUtils.join(restore_tableset, ","), "-o" };
    // Run backup
    int ret = ToolRunner.run(conf1, new RestoreDriver(), args);

    assertTrue(ret == 0);
    HBaseAdmin hba = TEST_UTIL.getHBaseAdmin();
    assertTrue(hba.tableExists(table2));
    assertTrue(hba.tableExists(table3));
    hba.close();
  }

};