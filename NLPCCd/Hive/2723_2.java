//,temp,sample_3469.java,2,11,temp,sample_3470.java,2,13
//,3
public class xxx {
protected void reloadHashTable(byte pos, int partitionId) throws IOException, HiveException, SerDeException, ClassNotFoundException {
this.vectorMapJoinHashTable = null;
super.reloadHashTable(pos, partitionId);
MapJoinTableContainer smallTable = spilledMapJoinTables[pos];
vectorMapJoinHashTable = VectorMapJoinOptimizedCreateHashTable.createHashTable(conf, smallTable);
needHashTableSetup = true;
if (LOG.isDebugEnabled()) {


log.info("reloadhashtable");
}
}

};