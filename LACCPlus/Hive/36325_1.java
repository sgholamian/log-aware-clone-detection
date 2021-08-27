//,temp,SessionHiveMetaStoreClient.java,2179,2197,temp,SessionHiveMetaStoreClient.java,2159,2177
//,2
public class xxx {
  @Override
  protected NotNullConstraintsResponse getNotNullConstraintsInternal(NotNullConstraintsRequest req) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.NOT_NULL_CONSTRAINTS, req);
      NotNullConstraintsResponse v = (NotNullConstraintsResponse) queryCache.get(cacheKey);
      if (v == null) {
        v = super.getNotNullConstraintsInternal(req);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=getNotNullConstraintsInternal, dbName={}, tblName={}",
            req.getDb_name(), req.getTbl_name());
      }
      return v;
    }
    return super.getNotNullConstraintsInternal(req);
  }

};