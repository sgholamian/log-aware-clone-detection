//,temp,ObjectStore.java,9582,9613,temp,ObjectStore.java,9473,9507
//,3
public class xxx {
  @Override
  public List<ColumnStatistics> getTableColumnStatistics(
      String catName,
      String dbName,
      String tableName,
      List<String> colNames) throws MetaException, NoSuchObjectException {
    // Note: this will get stats without verifying ACID.
    boolean committed = false;
    Query query = null;
    List<ColumnStatistics> result = new ArrayList<>();

    try {
      openTransaction();
      query = pm.newQuery(MTableColumnStatistics.class);
      query.setResult("DISTINCT engine");
      Collection names = (Collection) query.execute();
      List<String> engines = new ArrayList<>();
      for (Iterator i = names.iterator(); i.hasNext();) {
        engines.add((String) i.next());
      }
      for (String e : engines) {
        ColumnStatistics cs = getTableColumnStatisticsInternal(
            catName, dbName, tableName, colNames, e, true, true);
        if (cs != null) {
          result.add(cs);
        }
      }
      committed = commitTransaction();
      return result;
    } finally {
      LOG.debug("Done executing getTableColumnStatistics with status : {}",
          committed);
      rollbackAndCleanup(committed, query);
    }
  }

};