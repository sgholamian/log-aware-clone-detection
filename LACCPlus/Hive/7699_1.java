//,temp,ObjectStore.java,8278,8305,temp,ObjectStore.java,8176,8206
//,3
public class xxx {
  @Override
  public List<HiveObjectPrivilege> listPrincipalTableGrantsAll(String principalName,
      PrincipalType principalType) {
    boolean success = false;
    Query query = null;
    try {
      openTransaction();
      LOG.debug("Executing listPrincipalAllTableGrants");
      List<MTablePrivilege> mSecurityTabPartList;
      if (principalName != null && principalType != null) {
        query = pm.newQuery(MTablePrivilege.class, "principalName == t1 && principalType == t2");
        query.declareParameters("java.lang.String t1, java.lang.String t2");
        mSecurityTabPartList =
            (List<MTablePrivilege>) query.execute(principalName, principalType.toString());
      } else {
        query = pm.newQuery(MTablePrivilege.class);
        mSecurityTabPartList = (List<MTablePrivilege>) query.execute();
      }
      LOG.debug("Done executing query for listPrincipalAllTableGrants");
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