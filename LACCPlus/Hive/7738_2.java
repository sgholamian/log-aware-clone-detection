//,temp,SessionHiveMetaStoreClient.java,2350,2370,temp,SessionHiveMetaStoreClient.java,2308,2327
//,3
public class xxx {
  protected List<String> listPartitionNamesInternal(String catName, String dbName, String tableName,
       List<String> partVals, int maxParts) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.LIST_PARTITIONS,
          catName, dbName, tableName, partVals, maxParts);
      List<String> v = (List<String>) queryCache.get(cacheKey);
      if (v == null) {
        v = super.listPartitionNamesInternal(catName, dbName, tableName, partVals, maxParts);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=listPartitionNamesInternal, dbName={}, tblName={}",
            dbName, tableName);
      }
      return v;
    }
    return super.listPartitionNamesInternal(catName, dbName, tableName, partVals, maxParts);
  }

};