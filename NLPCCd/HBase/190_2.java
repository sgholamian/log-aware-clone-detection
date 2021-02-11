//,temp,sample_3915.java,2,8,temp,sample_3381.java,2,9
//,3
public class xxx {
public void testBackupDeleteRestore() throws Exception {
List<TableName> tables = Lists.newArrayList(table1);
String backupId = fullTableBackup(tables);
assertTrue(checkSucceeded(backupId));


log.info("backup complete");
}

};