//,temp,ObjectStore.java,7607,7634,temp,ObjectStore.java,7577,7605
//,3
public class xxx {
  private List<MTablePrivilege> listAllTableGrants(String catName, String dbName, String tableName) {
    boolean success = false;
    Query query = null;
    List<MTablePrivilege> mSecurityTabList = new ArrayList<>();
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);
    try {
      LOG.debug("Executing listAllTableGrants");

      openTransaction();
      String queryStr = "table.tableName == t1 && table.database.name == t2" +
          "&& table.database.catalogName == t3";
      query = pm.newQuery(MTablePrivilege.class, queryStr);
      query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
      List<MTablePrivilege> mPrivs  =
          (List<MTablePrivilege>) query.executeWithArray(tableName, dbName, catName);
      LOG.debug("Done executing query for listAllTableGrants");
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityTabList.addAll(mPrivs);

      LOG.debug("Done retrieving all objects for listAllTableGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityTabList;
  }

};