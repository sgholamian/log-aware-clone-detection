//,temp,SessionHiveMetaStoreClient.java,2395,2414,temp,SessionHiveMetaStoreClient.java,2329,2348
//,2
public class xxx {
  @Override
  protected GetPartitionsPsWithAuthResponse listPartitionsWithAuthInfoRequestInternal(GetPartitionsPsWithAuthRequest req)
      throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.LIST_PARTITIONS_AUTH_INFO_REQ, req);
      GetPartitionsPsWithAuthResponse v = (GetPartitionsPsWithAuthResponse) queryCache.get(cacheKey);
      if (v == null) {
        v = super.listPartitionsWithAuthInfoRequestInternal(req);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=listPartitionsWithAuthInfoRequestInternal, dbName={}, tblName={}, partVals={}",
            req.getDbName(), req.getTblName(), req.getPartVals());
      }
      return v;
    }
    return super.listPartitionsWithAuthInfoRequestInternal(req);
  }

};