//,temp,SessionHiveMetaStoreClient.java,2350,2370,temp,SessionHiveMetaStoreClient.java,2308,2327
//,3
public class xxx {
  @Override
  protected List<Partition> listPartitionsWithAuthInfoInternal(String catName, String dbName, String tableName,
      int maxParts, String userName, List<String> groupNames) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.LIST_PARTITIONS_AUTH_INFO_ALL,
          catName, dbName, tableName, maxParts, userName, groupNames);
      List<Partition> v = (List<Partition>) queryCache.get(cacheKey);
      if (v == null) {
        v = super.listPartitionsWithAuthInfoInternal(catName, dbName, tableName, maxParts, userName, groupNames);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=listPartitionsWithAuthInfoInternalAll, dbName={}, tblName={}",
            dbName, tableName);
      }
      return v;
    }
    return super.listPartitionsWithAuthInfoInternal(catName, dbName, tableName, maxParts, userName, groupNames);
  }

};