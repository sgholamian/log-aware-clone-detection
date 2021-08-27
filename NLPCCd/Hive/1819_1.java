//,temp,sample_956.java,2,12,temp,sample_957.java,2,16
//,3
public class xxx {
private int renamePartition(Hive db, RenamePartitionDesc renamePartitionDesc) throws HiveException {
String tableName = renamePartitionDesc.getTableName();
LinkedHashMap<String, String> oldPartSpec = renamePartitionDesc.getOldPartSpec();
if (!allowOperationInReplicationScope(db, tableName, oldPartSpec, renamePartitionDesc.getReplicationSpec())) {
if (LOG.isDebugEnabled()) {


log.info("ddltask rename partition is skipped as table partition is newer than update");
}
}
}

};