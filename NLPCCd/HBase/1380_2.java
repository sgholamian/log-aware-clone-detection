//,temp,sample_5915.java,2,8,temp,sample_2554.java,2,9
//,3
public class xxx {
public void testFullRestoreSingleOverwriteCommand() throws Exception {
List<TableName> tables = Lists.newArrayList(table1);
String backupId = fullTableBackup(tables);
assertTrue(checkSucceeded(backupId));


log.info("backup complete");
}

};