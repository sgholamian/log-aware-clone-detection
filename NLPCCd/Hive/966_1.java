//,temp,sample_3161.java,2,10,temp,sample_3163.java,2,15
//,3
public class xxx {
protected void reloadHashTable(byte pos, int partitionId) throws IOException, HiveException, SerDeException, ClassNotFoundException {
HybridHashTableContainer container = (HybridHashTableContainer)mapJoinTables[pos];
HashPartition partition = container.getHashPartitions()[partitionId];
KeyValueContainer kvContainer = partition.getSidefileKVContainer();
int rowCount = kvContainer.size();


log.info("hybrid grace hash join number of rows restored from keyvaluecontainer");
}

};