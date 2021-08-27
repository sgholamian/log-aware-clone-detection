//,temp,ObjectStore.java,7930,7973,temp,ObjectStore.java,7855,7895
//,3
public class xxx {
  private List<MTablePrivilege> listAllMTableGrants(
      String principalName, PrincipalType principalType, String catName, String dbName,
      String tableName, String authorizer) {
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);
    boolean success = false;
    Query query = null;
    List<MTablePrivilege> mSecurityTabPartList = new ArrayList<>();
    try {
      openTransaction();
      LOG.debug("Executing listAllTableGrants");
      List<MTablePrivilege> mPrivs;
      if (authorizer != null) {
        query = pm.newQuery(MTablePrivilege.class,
            "principalName == t1 && principalType == t2 && table.tableName == t3 &&" +
                "table.database.name == t4 && table.database.catalogName == t5 && authorizer == t6");
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3," +
            "java.lang.String t4, java.lang.String t5, java.lang.String t6");
        mPrivs = (List<MTablePrivilege>) query.executeWithArray(principalName, principalType.toString(),
            tableName, dbName, catName, authorizer);
      } else {
        query = pm.newQuery(MTablePrivilege.class,
                "principalName == t1 && principalType == t2 && table.tableName == t3 &&" +
                    "table.database.name == t4 && table.database.catalogName == t5");
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3," +
                "java.lang.String t4, java.lang.String t5");
        mPrivs = (List<MTablePrivilege>) query.executeWithArray(principalName, principalType.toString(),
                tableName, dbName, catName);
      }
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityTabPartList.addAll(mPrivs);

      LOG.debug("Done retrieving all objects for listAllTableGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityTabPartList;
  }

};