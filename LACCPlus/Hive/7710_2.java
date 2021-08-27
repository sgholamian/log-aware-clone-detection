//,temp,ObjectStore.java,8583,8614,temp,ObjectStore.java,8415,8440
//,3
public class xxx {
  @Override
  public List<HiveObjectPrivilege> listPartitionGrantsAll(String catName, String dbName, String tableName,
      String partitionName) {
    boolean success = false;
    Query query = null;
    try {
      openTransaction();
      LOG.debug("Executing listPrincipalPartitionGrantsAll");
      query =
          pm.newQuery(MPartitionPrivilege.class,
              "partition.table.tableName == t3 && partition.table.database.name == t4 && "
                  + "partition.table.database.catalogName == t5 && partition.partitionName == t6");
      query.declareParameters("java.lang.String t3, java.lang.String t4, java.lang.String t5, " +
          "java.lang.String t6");
      List<MPartitionPrivilege> mSecurityTabPartList =
          (List<MPartitionPrivilege>) query.executeWithArray(tableName, dbName, catName, partitionName);
      LOG.debug("Done executing query for listPrincipalPartitionGrantsAll");
      pm.retrieveAll(mSecurityTabPartList);
      List<HiveObjectPrivilege> result = convertPartition(mSecurityTabPartList);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listPrincipalPartitionGrantsAll");
      return result;
    } finally {
      rollbackAndCleanup(success, query);
    }
  }

};