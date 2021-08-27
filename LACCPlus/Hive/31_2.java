//,temp,RemoteCompactorThread.java,50,57,temp,MetaStoreCompactorThread.java,61,68
//,3
public class xxx {
  @Override Table resolveTable(CompactionInfo ci) throws MetaException {
    try {
      return getMSForConf(conf).getTable(getDefaultCatalog(conf), ci.dbname, ci.tableName);
    } catch (MetaException e) {
      LOG.error("Unable to find table " + ci.getFullTableName() + ", " + e.getMessage());
      throw e;
    }
  }

};