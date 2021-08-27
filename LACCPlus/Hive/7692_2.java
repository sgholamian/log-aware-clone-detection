//,temp,ObjectStore.java,7751,7778,temp,ObjectStore.java,7684,7712
//,3
public class xxx {
  private List<MPartitionColumnPrivilege> listTableAllPartitionColumnGrants(
      String catName, String dbName, String tableName) {
    boolean success = false;
    Query query = null;
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);
    List<MPartitionColumnPrivilege> mSecurityColList = new ArrayList<>();
    try {
      LOG.debug("Executing listTableAllPartitionColumnGrants");

      openTransaction();
      String queryStr = "partition.table.tableName == t1 && partition.table.database.name == t2 " +
          "&& partition.table.database.catalogName == t3";
      query = pm.newQuery(MPartitionColumnPrivilege.class, queryStr);
      query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
      List<MPartitionColumnPrivilege> mPrivs =
          (List<MPartitionColumnPrivilege>) query.executeWithArray(tableName, dbName, catName);
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityColList.addAll(mPrivs);

      LOG.debug("Done retrieving all objects for listTableAllPartitionColumnGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityColList;
  }

};