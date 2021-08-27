//,temp,SessionHiveMetaStoreClient.java,1795,1804,temp,SessionHiveMetaStoreClient.java,1779,1794
//,3
public class xxx {
  private TempTable getPartitionedTempTable(org.apache.hadoop.hive.metastore.api.Table t) throws MetaException {
    String qualifiedTableName = Warehouse.
        getQualifiedName(t.getDbName().toLowerCase(), t.getTableName().toLowerCase());
    SessionState ss = SessionState.get();
    if (ss == null) {
      LOG.warn("No current SessionState, skipping temp partitions for " + qualifiedTableName);
      return null;
    }
    assertTempTablePartitioned(t);
    TempTable tt = ss.getTempPartitions().get(qualifiedTableName);
    if (tt == null) {
      throw new IllegalStateException("TempTable not found for " +
          getCatalogQualifiedTableName(t));
    }
    return tt;
  }

};