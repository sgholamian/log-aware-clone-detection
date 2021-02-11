//,temp,sample_3722.java,2,9,temp,sample_2552.java,2,9
//,2
public class xxx {
public void testBackupDeleteCommand() throws Exception {
List<TableName> tableList = Lists.newArrayList(table1);
String backupId = fullTableBackup(tableList);
assertTrue(checkSucceeded(backupId));


log.info("backup complete");
}

};