//,temp,ObjectStore.java,7607,7634,temp,ObjectStore.java,7577,7605
//,3
public class xxx {
  private List<MPartitionPrivilege> listTableAllPartitionGrants(String catName, String dbName, String tableName) {
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);
    boolean success = false;
    Query query = null;
    List<MPartitionPrivilege> mSecurityTabPartList = new ArrayList<>();
    try {
      LOG.debug("Executing listTableAllPartitionGrants");

      openTransaction();
      String queryStr = "partition.table.tableName == t1 && partition.table.database.name == t2 " +
          "&& partition.table.database.catalogName == t3";
      query = pm.newQuery(MPartitionPrivilege.class, queryStr);
      query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
      List<MPartitionPrivilege> mPrivs =
          (List<MPartitionPrivilege>) query.executeWithArray(tableName, dbName, catName);
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityTabPartList.addAll(mPrivs);

      LOG.debug("Done retrieving all objects for listTableAllPartitionGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityTabPartList;
  }

};