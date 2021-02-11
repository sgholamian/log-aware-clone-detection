//,temp,sample_3414.java,2,16,temp,sample_3415.java,2,16
//,3
public class xxx {
public void dummy_method(){
insertIntoTable(conn, table1, fam3Name, 3, NB_ROWS_FAM3).close();
HBaseAdmin admin = null;
admin = (HBaseAdmin) conn.getAdmin();
BackupAdminImpl client = new BackupAdminImpl(conn);
BackupRequest request = createBackupRequest(BackupType.FULL, tables, BACKUP_ROOT_DIR);
String backupIdFull = client.backupTables(request);
assertTrue(checkSucceeded(backupIdFull));
HTable t1 = insertIntoTable(conn, table1, famName, 1, ADD_ROWS);
Assert.assertEquals(TEST_UTIL.countRows(t1), NB_ROWS_IN_BATCH + ADD_ROWS + NB_ROWS_FAM3);
t1.close();


log.info("written rows to");
}

};