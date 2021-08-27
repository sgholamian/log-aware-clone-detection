//,temp,ObjectStore.java,8367,8383,temp,ObjectStore.java,8259,8276
//,2
public class xxx {
  private List<MPartitionPrivilege> listPrincipalAllPartitionGrants(String principalName, PrincipalType principalType)
      throws Exception {
    LOG.debug("Executing listPrincipalAllPartitionGrants");

    Preconditions.checkState(this.currentTransaction.isActive());

    try (Query query = pm.newQuery(MPartitionPrivilege.class, "principalName == t1 && principalType == t2")) {
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      final List<MPartitionPrivilege> mSecurityTabPartList =
          (List<MPartitionPrivilege>) query.execute(principalName, principalType.toString());

      pm.retrieveAll(mSecurityTabPartList);
      LOG.debug("Done retrieving all objects for listPrincipalAllPartitionGrants");

      return Collections.unmodifiableList(new ArrayList<>(mSecurityTabPartList));
    }
  }

};