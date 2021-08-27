//,temp,ObjectStore.java,8013,8058,temp,ObjectStore.java,7641,7682
//,3
public class xxx {
  private List<MTableColumnPrivilege> listPrincipalMTableColumnGrants(
      String principalName, PrincipalType principalType, String catName, String dbName,
      String tableName, String columnName, String authorizer) {
    boolean success = false;
    Query query = null;
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    columnName = normalizeIdentifier(columnName);
    List<MTableColumnPrivilege> mSecurityColList = new ArrayList<>();
    try {
      LOG.debug("Executing listPrincipalTableColumnGrants");

      openTransaction();
      List<MTableColumnPrivilege> mPrivs;
      if (authorizer != null) {
        String queryStr =
            "principalName == t1 && principalType == t2 && "
                + "table.tableName == t3 && table.database.name == t4 &&  " +
                "table.database.catalogName == t5 && columnName == t6 && authorizer == t7";
        query = pm.newQuery(MTableColumnPrivilege.class, queryStr);
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, "
            + "java.lang.String t4, java.lang.String t5, java.lang.String t6, java.lang.String t7");
        mPrivs = (List<MTableColumnPrivilege>) query.executeWithArray(principalName,
                principalType.toString(), tableName, dbName, catName, columnName, authorizer);
      } else {
        String queryStr =
            "principalName == t1 && principalType == t2 && "
                + "table.tableName == t3 && table.database.name == t4 &&  " +
                "table.database.catalogName == t5 && columnName == t6 ";
        query = pm.newQuery(MTableColumnPrivilege.class, queryStr);
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, "
            + "java.lang.String t4, java.lang.String t5, java.lang.String t6");
        mPrivs = (List<MTableColumnPrivilege>) query.executeWithArray(principalName,
                principalType.toString(), tableName, dbName, catName, columnName);
      }
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityColList.addAll(mPrivs);

      LOG.debug("Done retrieving all objects for listPrincipalTableColumnGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityColList;
  }

};