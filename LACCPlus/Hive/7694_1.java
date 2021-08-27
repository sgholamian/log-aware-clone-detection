//,temp,ObjectStore.java,8208,8235,temp,ObjectStore.java,6235,6271
//,3
public class xxx {
  @Override
  public List<HiveObjectPrivilege> listPartitionColumnGrantsAll(
      String catName, String dbName, String tableName, String partitionName, String columnName) {
    boolean success = false;
    Query query = null;
    try {
      openTransaction();
      LOG.debug("Executing listPartitionColumnGrantsAll");
      query =
          pm.newQuery(MPartitionColumnPrivilege.class,
              "partition.table.tableName == t3 && partition.table.database.name == t4 && "
                  + "partition.table.database.name == t5 && "
                  + "partition.partitionName == t6 && columnName == t7");
      query.declareParameters("java.lang.String t3, java.lang.String t4, java.lang.String t5," +
          "java.lang.String t6, java.lang.String t7");
      List<MPartitionColumnPrivilege> mSecurityTabPartList =
          (List<MPartitionColumnPrivilege>) query.executeWithArray(tableName, dbName, catName,
              partitionName, columnName);
      LOG.debug("Done executing query for listPartitionColumnGrantsAll");
      pm.retrieveAll(mSecurityTabPartList);
      List<HiveObjectPrivilege> result = convertPartCols(mSecurityTabPartList);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listPartitionColumnGrantsAll");
      return result;
    } finally {
      rollbackAndCleanup(success, query);
    }
  }

};