//,temp,ObjectStore.java,8385,8413,temp,ObjectStore.java,8312,8344
//,3
public class xxx {
  @Override
  public List<HiveObjectPrivilege> listPrincipalPartitionGrantsAll(String principalName,
      PrincipalType principalType) {
    boolean success = false;
    Query query = null;
    try {
      openTransaction();
      LOG.debug("Executing listPrincipalPartitionGrantsAll");
      List<MPartitionPrivilege> mSecurityTabPartList;
      if (principalName != null && principalType != null) {
        query =
            pm.newQuery(MPartitionPrivilege.class, "principalName == t1 && principalType == t2");
        query.declareParameters("java.lang.String t1, java.lang.String t2");
        mSecurityTabPartList =
            (List<MPartitionPrivilege>) query.execute(principalName, principalType.toString());
      } else {
        query = pm.newQuery(MPartitionPrivilege.class);
        mSecurityTabPartList = (List<MPartitionPrivilege>) query.execute();
      }
      LOG.debug("Done executing query for listPrincipalPartitionGrantsAll");
      pm.retrieveAll(mSecurityTabPartList);
      List<HiveObjectPrivilege> result = convertPartition(mSecurityTabPartList);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listPrincipalPartitionGrantsAll");
      return result;
    } finally {
      rollbackAndCleanup(success, query);
    }
  }

};