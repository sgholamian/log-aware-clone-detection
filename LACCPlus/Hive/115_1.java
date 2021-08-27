//,temp,ObjectStore.java,7780,7804,temp,ObjectStore.java,7714,7739
//,3
public class xxx {
  private List<MPartitionPrivilege> listPartitionGrants(String catName, String dbName, String tableName,
      List<String> partNames) {
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);

    boolean success = false;
    List<MPartitionPrivilege> mSecurityTabPartList = null;
    try {
      openTransaction();
      LOG.debug("Executing listPartitionGrants");
      mSecurityTabPartList = queryByPartitionNames(catName,
          dbName, tableName, partNames, MPartitionPrivilege.class, "partition.table.tableName",
          "partition.table.database.name", "partition.partitionName",
          "partition.table.database.catalogName");
      LOG.debug("Done executing query for listPartitionGrants");
      pm.retrieveAll(mSecurityTabPartList);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listPartitionGrants");
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return mSecurityTabPartList;
  }

};