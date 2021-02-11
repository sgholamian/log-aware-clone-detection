//,temp,sample_5917.java,2,8,temp,sample_2558.java,2,9
//,3
public class xxx {
public void testFullRestoreSingleDNE() throws Exception {
List<TableName> tables = Lists.newArrayList(table1);
String backupId = fullTableBackup(tables);
assertTrue(checkSucceeded(backupId));


log.info("backup complete");
}

};