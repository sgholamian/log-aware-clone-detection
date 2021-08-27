//,temp,sample_3160.java,2,8,temp,sample_3166.java,2,8
//,3
public class xxx {
protected void reloadHashTable(byte pos, int partitionId) throws IOException, HiveException, SerDeException, ClassNotFoundException {
HybridHashTableContainer container = (HybridHashTableContainer)mapJoinTables[pos];
HashPartition partition = container.getHashPartitions()[partitionId];


log.info("going to restore sidefile");
}

};