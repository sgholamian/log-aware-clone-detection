//,temp,Hive.java,5516,5524,temp,Hive.java,5506,5514
//,3
public class xxx {
  public boolean deletePartitionColumnStatistics(String dbName, String tableName, String partName,
    String colName) throws HiveException {
      try {
        return getMSC().deletePartitionColumnStatistics(dbName, tableName, partName, colName, Constants.HIVE_ENGINE);
      } catch(Exception e) {
        LOG.debug("Failed deletePartitionColumnStatistics", e);
        throw new HiveException(e);
      }
    }

};