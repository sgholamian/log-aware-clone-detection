//,temp,sample_3161.java,2,10,temp,sample_3163.java,2,15
//,3
public class xxx {
protected void reloadHashTable(byte pos, int partitionId) throws IOException, HiveException, SerDeException, ClassNotFoundException {
HybridHashTableContainer container = (HybridHashTableContainer)mapJoinTables[pos];
HashPartition partition = container.getHashPartitions()[partitionId];
KeyValueContainer kvContainer = partition.getSidefileKVContainer();
int rowCount = kvContainer.size();
if (rowCount <= 0) {
rowCount = 1024 * 1024;
}
BytesBytesMultiHashMap restoredHashMap = partition.getHashMapFromDisk(rowCount);
rowCount += restoredHashMap.getNumValues();


log.info("hybrid grace hash join deserializing spilled hash partition");
}

};