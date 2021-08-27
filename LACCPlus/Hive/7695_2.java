//,temp,ObjectStore.java,8013,8058,temp,ObjectStore.java,7641,7682
//,3
public class xxx {
  private List<MTableColumnPrivilege> listTableAllColumnGrants(
      String catName, String dbName, String tableName, String authorizer) {
    boolean success = false;
    Query query = null;
    List<MTableColumnPrivilege> mTblColPrivilegeList = new ArrayList<>();
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);
    try {
      LOG.debug("Executing listTableAllColumnGrants");

      openTransaction();
      List<MTableColumnPrivilege> mPrivs = null;
      if (authorizer != null) {
        String queryStr = "table.tableName == t1 && table.database.name == t2 &&" +
            "table.database.catalogName == t3 && authorizer == t4";
        query = pm.newQuery(MTableColumnPrivilege.class, queryStr);
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, " +
            "java.lang.String t4");
        mPrivs = (List<MTableColumnPrivilege>) query.executeWithArray(tableName, dbName, catName, authorizer);
      } else {
        String queryStr = "table.tableName == t1 && table.database.name == t2 &&" +
            "table.database.catalogName == t3";
        query = pm.newQuery(MTableColumnPrivilege.class, queryStr);
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
        mPrivs = (List<MTableColumnPrivilege>) query.executeWithArray(tableName, dbName, catName);
      }
      LOG.debug("Query to obtain objects for listTableAllColumnGrants finished");
      pm.retrieveAll(mPrivs);
      LOG.debug("RetrieveAll on all the objects for listTableAllColumnGrants finished");
      success = commitTransaction();
      LOG.debug("Transaction running query to obtain objects for listTableAllColumnGrants " +
              "committed");

      mTblColPrivilegeList.addAll(mPrivs);

      LOG.debug("Done retrieving " + mPrivs.size() + " objects for listTableAllColumnGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mTblColPrivilegeList;
  }

};