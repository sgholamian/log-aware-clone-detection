//,temp,ObjectStore.java,8208,8235,temp,ObjectStore.java,6235,6271
//,3
public class xxx {
  public List<MRoleMap> listMRoles(String principalName,
      PrincipalType principalType) {
    boolean success = false;
    Query query = null;
    List<MRoleMap> mRoleMember = new ArrayList<>();

    try {
      LOG.debug("Executing listRoles");

      openTransaction();
      query = pm.newQuery(MRoleMap.class, "principalName == t1 && principalType == t2");
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      query.setUnique(false);
      List<MRoleMap> mRoles =
          (List<MRoleMap>) query.executeWithArray(principalName, principalType.toString());
      pm.retrieveAll(mRoles);
      success = commitTransaction();

      mRoleMember.addAll(mRoles);

      LOG.debug("Done retrieving all objects for listRoles");
    } finally {
      rollbackAndCleanup(success, query);
    }

    if (principalType == PrincipalType.USER) {
      // All users belong to public role implicitly, add that role
      // TODO MS-SPLIT Change this back to HMSHandler.PUBLIC once HiveMetaStore has moved to
      // stand-alone metastore.
      //MRole publicRole = new MRole(HMSHandler.PUBLIC, 0, HMSHandler.PUBLIC);
      MRole publicRole = new MRole("public", 0, "public");
      mRoleMember.add(new MRoleMap(principalName, principalType.toString(), publicRole, 0, null,
          null, false));
    }

    return mRoleMember;
  }

};