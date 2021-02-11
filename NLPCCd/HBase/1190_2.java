//,temp,sample_2546.java,2,8,temp,sample_2807.java,2,9
//,3
public class xxx {
public void testBackupHistory() throws Exception {
List<TableName> tableList = Lists.newArrayList(table1);
String backupId = fullTableBackup(tableList);
assertTrue(checkSucceeded(backupId));


log.info("backup complete");
}

};