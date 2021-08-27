//,temp,Hive.java,3316,3325,temp,Hive.java,3285,3294
//,3
public class xxx {
  public void alterPartitions(String dbName, String tableName,
      List<org.apache.hadoop.hive.metastore.api.Partition> partitions, EnvironmentContext ec, String validWriteIdList,
      long writeId) throws HiveException {
    try {
      getMSC().alter_partitions(dbName, tableName, partitions, ec, validWriteIdList, writeId);
    } catch (Exception e) {
      LOG.error("Failed alterPartitions", e);
      throw new HiveException(e);
    }
  }

};