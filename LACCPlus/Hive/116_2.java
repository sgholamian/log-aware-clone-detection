//,temp,ObjectStore.java,8565,8581,temp,ObjectStore.java,6311,6327
//,3
public class xxx {
  private List<MRoleMap> listMSecurityPrincipalMembershipRole(final String roleName,
      final PrincipalType principalType) throws Exception {
    LOG.debug("Executing listMSecurityPrincipalMembershipRole");

    Preconditions.checkState(this.currentTransaction.isActive());

    try (Query query = pm.newQuery(MRoleMap.class, "principalName == t1 && principalType == t2")) {
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      final List<MRoleMap> mRoleMemebership = (List<MRoleMap>) query.execute(roleName, principalType.toString());

      LOG.debug("Retrieving all objects for listMSecurityPrincipalMembershipRole");
      pm.retrieveAll(mRoleMemebership);
      LOG.debug("Done retrieving all objects for listMSecurityPrincipalMembershipRole: {}", mRoleMemebership);

      return Collections.unmodifiableList(new ArrayList<>(mRoleMemebership));
    }
  }

};