//,temp,ObjectStore.java,8483,8512,temp,ObjectStore.java,7433,7470
//,3
public class xxx {
  private List<MDBPrivilege> listPrincipalMDBGrants(String principalName,
      PrincipalType principalType, String catName, String dbName, String authorizer) {
    boolean success = false;
    Query query = null;
    List<MDBPrivilege> mSecurityDBList = new ArrayList<>();
    dbName = normalizeIdentifier(dbName);
    try {
      LOG.debug("Executing listPrincipalDBGrants");

      openTransaction();
      List<MDBPrivilege> mPrivs;
      if (authorizer != null) {
        query = pm.newQuery(MDBPrivilege.class,
            "principalName == t1 && principalType == t2 && database.name == t3 && " +
            "database.catalogName == t4 && authorizer == t5");
        query.declareParameters(
            "java.lang.String t1, java.lang.String t2, java.lang.String t3, java.lang.String t4, "
            + "java.lang.String t5");
        mPrivs = (List<MDBPrivilege>) query.executeWithArray(principalName, principalType.toString(),
                dbName, catName, authorizer);
      } else {
        query = pm.newQuery(MDBPrivilege.class,
                "principalName == t1 && principalType == t2 && database.name == t3 && database.catalogName == t4");
        query.declareParameters(
            "java.lang.String t1, java.lang.String t2, java.lang.String t3, java.lang.String t4");
        mPrivs = (List<MDBPrivilege>) query.executeWithArray(principalName, principalType.toString(),
                dbName, catName);
      }
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityDBList.addAll(mPrivs);
      LOG.debug("Done retrieving all objects for listPrincipalDBGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityDBList;
  }

};