//,temp,ObjectStore.java,10248,10270,temp,ObjectStore.java,693,713
//,3
public class xxx {
  @Override
  public List<String> getCatalogs() throws MetaException {
    LOG.debug("Fetching all catalog names");
    boolean commited = false;
    List<String> catalogs = null;

    String queryStr = "select name from org.apache.hadoop.hive.metastore.model.MCatalog";
    Query query = null;

    openTransaction();
    try {
      query = pm.newQuery(queryStr);
      query.setResult("name");
      catalogs = new ArrayList<>((Collection<String>) query.execute());
      commited = commitTransaction();
    } finally {
      rollbackAndCleanup(commited, query);
    }
    Collections.sort(catalogs);
    return catalogs;
  }

};