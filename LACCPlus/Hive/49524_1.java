//,temp,ObjectStore.java,7930,7973,temp,ObjectStore.java,7855,7895
//,3
public class xxx {
  private List<MPartitionPrivilege> listPrincipalMPartitionGrants(
      String principalName, PrincipalType principalType, String catName, String dbName,
      String tableName, String partName, String authorizer) {
    boolean success = false;
    Query query = null;
    tableName = normalizeIdentifier(tableName);
    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);
    List<MPartitionPrivilege> mSecurityTabPartList = new ArrayList<>();
    try {
      LOG.debug("Executing listPrincipalPartitionGrants");

      openTransaction();
      List<MPartitionPrivilege> mPrivs;
      if (authorizer != null) {
        query = pm.newQuery(MPartitionPrivilege.class,
            "principalName == t1 && principalType == t2 && partition.table.tableName == t3 "
                + "&& partition.table.database.name == t4 && partition.table.database.catalogName == t5"
                + "&& partition.partitionName == t6 && authorizer == t7");
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, java.lang.String t4, "
                + "java.lang.String t5, java.lang.String t6, java.lang.String t7");
        mPrivs = (List<MPartitionPrivilege>) query.executeWithArray(principalName,
        principalType.toString(), tableName, dbName, catName, partName, authorizer);
      } else {
        query = pm.newQuery(MPartitionPrivilege.class,
                "principalName == t1 && principalType == t2 && partition.table.tableName == t3 "
                    + "&& partition.table.database.name == t4 && partition.table.database.catalogName == t5"
                    + "&& partition.partitionName == t6");
        query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, java.lang.String t4, "
                + "java.lang.String t5, java.lang.String t6");
        mPrivs = (List<MPartitionPrivilege>) query.executeWithArray(principalName,
            principalType.toString(), tableName, dbName, catName, partName);
      }
      pm.retrieveAll(mPrivs);
      success = commitTransaction();

      mSecurityTabPartList.addAll(mPrivs);

      LOG.debug("Done retrieving all objects for listPrincipalPartitionGrants");
    } finally {
      rollbackAndCleanup(success, query);
    }
    return mSecurityTabPartList;
  }

};