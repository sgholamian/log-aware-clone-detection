//,temp,ObjectStore.java,7751,7778,temp,ObjectStore.java,7684,7712
//,3
public class xxx {
  private List<MDBPrivilege> listDatabaseGrants(String catName, String dbName, String authorizer) throws Exception {
    LOG.debug("Executing listDatabaseGrants");

    Preconditions.checkState(currentTransaction.isActive());

    dbName = normalizeIdentifier(dbName);
    catName = normalizeIdentifier(catName);

    final Query query;
    final String[] args;

    if (authorizer != null) {
      query = pm.newQuery(MDBPrivilege.class, "database.name == t1 && database.catalogName == t2 && authorizer == t3");
      query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
      args = new String[] { dbName, catName, authorizer };
    } else {
      query = pm.newQuery(MDBPrivilege.class, "database.name == t1 && database.catalogName == t2");
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      args = new String[] { dbName, catName };
    }

    try (Query q = query) {
      final List<MDBPrivilege> mSecurityDBList = (List<MDBPrivilege>) query.executeWithArray(args);
      pm.retrieveAll(mSecurityDBList);
      LOG.debug("Done retrieving all objects for listDatabaseGrants: {}", mSecurityDBList);
      return Collections.unmodifiableList(new ArrayList<>(mSecurityDBList));
    }
  }

};