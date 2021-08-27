//,temp,sample_3160.java,2,8,temp,sample_3166.java,2,8
//,3
public class xxx {
protected void reProcessBigTable(int partitionId) throws HiveException {
HashPartition partition = firstSmallTable.getHashPartitions()[partitionId];
ObjectContainer bigTable = partition.getMatchfileObjContainer();


log.info("hybrid grace hash join going to process spilled big table rows in partition number of rows");
}

};