//,temp,SessionHiveMetaStoreClient.java,2286,2306,temp,SessionHiveMetaStoreClient.java,2047,2066
//,3
public class xxx {
  @Override
  protected List<String> listPartitionNamesInternal(String catName, String dbName, String tableName,
       int maxParts) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.LIST_PARTITIONS_ALL,
          catName, dbName, tableName, maxParts);
      List<String> v = (List<String>) queryCache.get(cacheKey);
      if (v == null) {
        v = super.listPartitionNamesInternal(catName, dbName, tableName, maxParts);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=listPartitionNamesInternalAll, dbName={}, tblName={}",
            dbName, tableName);
      }
      return v;
    }
    return super.listPartitionNamesInternal(catName, dbName, tableName, maxParts);
  }

};