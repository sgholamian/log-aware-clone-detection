//,temp,SessionHiveMetaStoreClient.java,2266,2284,temp,SessionHiveMetaStoreClient.java,2226,2244
//,3
public class xxx {
  @Override
  protected PartitionsSpecByExprResult getPartitionsSpecByExprInternal(PartitionsByExprRequest req) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.PARTITIONS_SPEC_BY_EXPR, req);
      PartitionsSpecByExprResult v = (PartitionsSpecByExprResult) queryCache.get(cacheKey);
      if (v == null) {
        v = super.getPartitionsSpecByExprInternal(req);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=getPartitionsSpecByExprInternal, dbName={}, tblName={}",
            req.getDbName(), req.getTblName());
      }
      return v;
    }
    return super.getPartitionsSpecByExprInternal(req);
  }

};