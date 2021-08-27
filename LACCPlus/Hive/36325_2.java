//,temp,SessionHiveMetaStoreClient.java,2179,2197,temp,SessionHiveMetaStoreClient.java,2159,2177
//,2
public class xxx {
  @Override
  protected UniqueConstraintsResponse getUniqueConstraintsInternal(UniqueConstraintsRequest req) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.UNIQUE_CONSTRAINTS, req);
      UniqueConstraintsResponse v = (UniqueConstraintsResponse) queryCache.get(cacheKey);
      if (v == null) {
        v = super.getUniqueConstraintsInternal(req);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=getUniqueConstraintsInternal, dbName={}, tblName={}",
            req.getDb_name(), req.getTbl_name());
      }
      return v;
    }
    return super.getUniqueConstraintsInternal(req);
  }

};