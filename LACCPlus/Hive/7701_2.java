//,temp,ObjectStore.java,8385,8413,temp,ObjectStore.java,8312,8344
//,3
public class xxx {
  private List<HiveObjectPrivilege> listTableGrantsAll(String catName, String dbName, String tableName,
      String authorizer) {
    boolean success = false;
    Query query = null;
    dbName = normalizeIdentifier(dbName);
    tableName = normalizeIdentifier(tableName);
    try {
      openTransaction();
      LOG.debug("Executing listTableGrantsAll");
      List<MTablePrivilege> mSecurityTabPartList = null;
      if (authorizer != null) {
        query = pm.newQuery(MTablePrivilege.class,
                "table.tableName == t1 && table.database.name == t2 && table.database.catalogName == t3" +
                " && authorizer == t4");
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, " +
                "java.lang.String t4");
        mSecurityTabPartList = (List<MTablePrivilege>) query.executeWithArray(tableName, dbName, catName, authorizer);
      } else {
        query = pm.newQuery(MTablePrivilege.class,
                "table.tableName == t1 && table.database.name == t2 && table.database.catalogName == t3");
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
        mSecurityTabPartList = (List<MTablePrivilege>) query.executeWithArray(tableName, dbName, catName);
      }
      LOG.debug("Done executing query for listTableGrantsAll");
      pm.retrieveAll(mSecurityTabPartList);
      List<HiveObjectPrivilege> result = convertTable(mSecurityTabPartList);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listPrincipalAllTableGrants");
      return result;
    } finally {
      rollbackAndCleanup(success, query);
    }
  }

};