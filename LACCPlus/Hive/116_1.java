//,temp,ObjectStore.java,8565,8581,temp,ObjectStore.java,6311,6327
//,3
public class xxx {
  private List<MPartitionColumnPrivilege> listPrincipalAllPartitionColumnGrants(String principalName,
      PrincipalType principalType) throws Exception {
    LOG.debug("Executing listPrincipalAllTableColumnGrants");

    Preconditions.checkState(this.currentTransaction.isActive());

    try (Query query = pm.newQuery(MPartitionColumnPrivilege.class, "principalName == t1 && principalType == t2")) {
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      final List<MPartitionColumnPrivilege> mSecurityColumnList =
          (List<MPartitionColumnPrivilege>) query.execute(principalName, principalType.toString());

      pm.retrieveAll(mSecurityColumnList);
      LOG.debug("Done retrieving all objects for listPrincipalAllTableColumnGrants");

      return Collections.unmodifiableList(new ArrayList<>(mSecurityColumnList));
    }
  }

};