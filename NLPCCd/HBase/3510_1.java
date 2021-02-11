//,temp,sample_2807.java,2,9,temp,sample_2558.java,2,9
//,2
public class xxx {
public void testBackupHistory() throws Exception {
List<TableName> tableList = Lists.newArrayList(table1);
String backupId = fullTableBackup(tableList);
assertTrue(checkSucceeded(backupId));


log.info("backup complete");
}

};