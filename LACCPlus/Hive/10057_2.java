//,temp,SessionHiveMetaStoreClient.java,2395,2414,temp,SessionHiveMetaStoreClient.java,2329,2348
//,2
public class xxx {
  @Override
  protected GetPartitionNamesPsResponse listPartitionNamesRequestInternal(GetPartitionNamesPsRequest req)
      throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.LIST_PARTITIONS_REQ, req);
      GetPartitionNamesPsResponse v = (GetPartitionNamesPsResponse) queryCache.get(cacheKey);
      if (v == null) {
        v = super.listPartitionNamesRequestInternal(req);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=listPartitionNamesRequestInternal, dbName={}, tblName={}, partValues={}",
            req.getDbName(), req.getTblName(), req.getPartValues());
      }
      return v;
    }
    return super.listPartitionNamesRequestInternal(req);
  }

};