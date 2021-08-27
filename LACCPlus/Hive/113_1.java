//,temp,ObjectStore.java,7285,7307,temp,ObjectStore.java,6357,6376
//,3
public class xxx {
  public List<MRoleMap> listMRoleMembers(String roleName) {
    boolean success = false;
    Query query = null;
    List<MRoleMap> mRoleMemeberList = new ArrayList<>();
    try {
      LOG.debug("Executing listRoleMembers");

      openTransaction();
      query = pm.newQuery(MRoleMap.class, "role.roleName == t1");
      query.declareParameters("java.lang.String t1");
      query.setUnique(false);
      List<MRoleMap> mRoles = (List<MRoleMap>) query.execute(roleName);
      pm.retrieveAll(mRoles);
      success = commitTransaction();

      mRoleMemeberList.addAll(mRoles);

      LOG.debug("Done retrieving all objects for listRoleMembers");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mRoleMemeberList;
  }

};