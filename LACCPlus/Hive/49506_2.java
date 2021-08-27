//,temp,Hive.java,5516,5524,temp,Hive.java,5506,5514
//,3
public class xxx {
  public boolean deleteTableColumnStatistics(String dbName, String tableName, String colName)
    throws HiveException {
    try {
      return getMSC().deleteTableColumnStatistics(dbName, tableName, colName, Constants.HIVE_ENGINE);
    } catch(Exception e) {
      LOG.debug("Failed deleteTableColumnStatistics", e);
      throw new HiveException(e);
    }
  }

};