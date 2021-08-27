//,temp,SessionHiveMetaStoreClient.java,1795,1804,temp,SessionHiveMetaStoreClient.java,1779,1794
//,3
public class xxx {
  private void removePartitionedTempTable(org.apache.hadoop.hive.metastore.api.Table t) {
    String qualifiedTableName = Warehouse.
        getQualifiedName(t.getDbName().toLowerCase(), t.getTableName().toLowerCase());
    SessionState ss = SessionState.get();
    if (ss == null) {
      LOG.warn("No current SessionState, skipping temp partitions for " + qualifiedTableName);
      return;
    }
    ss.getTempPartitions().remove(Warehouse.getQualifiedName(t));
  }

};