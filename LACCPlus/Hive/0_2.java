//,temp,ObjectStore.java,13621,13635,temp,ObjectStore.java,3258,3274
//,3
public class xxx {
  @Override
  public List<String> listPartitionNames(String catName, String dbName, String tableName,
      short max) throws MetaException {
    List<String> pns = null;
    boolean success = false;
    try {
      openTransaction();
      LOG.debug("Executing getPartitionNames");
      pns = getPartitionNamesNoTxn(catName, dbName, tableName, max);
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return pns;
  }

};