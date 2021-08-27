//,temp,HiveMetaStoreClientPreCatalog.java,2862,2874,temp,ObjectStore.java,9615,9625
//,3
public class xxx {
  @Override
  public List<ColumnStatistics> getPartitionColumnStatistics(String catName, String dbName, String tableName,
      List<String> partNames, List<String> colNames, String engine) throws MetaException, NoSuchObjectException {
    // Note: this will get stats without verifying ACID.
    if (CollectionUtils.isEmpty(partNames) || CollectionUtils.isEmpty(colNames)) {
      LOG.debug("PartNames and/or ColNames are empty");
      return Collections.emptyList();
    }
    return getPartitionColumnStatisticsInternal(
        catName, dbName, tableName, partNames, colNames, engine, true, true);
  }

};