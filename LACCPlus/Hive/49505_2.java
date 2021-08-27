//,temp,Hive.java,3316,3325,temp,Hive.java,3285,3294
//,3
public class xxx {
  public List<org.apache.hadoop.hive.metastore.api.Partition> addPartitions(
      List<org.apache.hadoop.hive.metastore.api.Partition> partitions, boolean ifNotExists, boolean needResults)
          throws HiveException {
    try {
      return getMSC().add_partitions(partitions, ifNotExists, needResults);
    } catch (Exception e) {
      LOG.error("Failed addPartitions", e);
      throw new HiveException(e);
    }
  }

};