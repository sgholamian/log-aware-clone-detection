//,temp,sample_3164.java,2,15,temp,sample_3162.java,2,13
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


log.info("hybrid grace hash join number of rows in hashmap");
}

};