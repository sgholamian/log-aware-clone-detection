//,temp,ObjectStore.java,7780,7804,temp,ObjectStore.java,7714,7739
//,3
public class xxx {
  private List<MPartitionColumnPrivilege> listPartitionAllColumnGrants(
      String catName, String dbName, String tableName, List<String> partNames) {
    boolean success = false;
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);

    List<MPartitionColumnPrivilege> mSecurityColList = null;
    try {
      openTransaction();
      LOG.debug("Executing listPartitionAllColumnGrants");
      mSecurityColList = queryByPartitionNames(catName,
          dbName, tableName, partNames, MPartitionColumnPrivilege.class,
          "partition.table.tableName", "partition.table.database.name", "partition.partitionName",
          "partition.table.database.catalogName");
      LOG.debug("Done executing query for listPartitionAllColumnGrants");
      pm.retrieveAll(mSecurityColList);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listPartitionAllColumnGrants");
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return mSecurityColList;
  }

};