//,temp,sample_956.java,2,12,temp,sample_957.java,2,16
//,3
public class xxx {
private int renamePartition(Hive db, RenamePartitionDesc renamePartitionDesc) throws HiveException {
String tableName = renamePartitionDesc.getTableName();
LinkedHashMap<String, String> oldPartSpec = renamePartitionDesc.getOldPartSpec();
if (!allowOperationInReplicationScope(db, tableName, oldPartSpec, renamePartitionDesc.getReplicationSpec())) {
if (LOG.isDebugEnabled()) {
}
return 0;
}
String names[] = Utilities.getDbTableName(tableName);
if (Utils.isBootstrapDumpInProgress(db, names[0])) {


log.info("ddltask rename partition not allowed as bootstrap dump in progress");
}
}

};