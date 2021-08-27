//,temp,SessionHiveMetaStoreClient.java,2266,2284,temp,SessionHiveMetaStoreClient.java,2226,2244
//,3
public class xxx {
  @Override
  protected AggrStats getAggrStatsForInternal(PartitionsStatsRequest req) throws TException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.AGGR_COL_STATS, req);
      AggrStats v = (AggrStats) queryCache.get(cacheKey);
      if (v == null) {
        v = super.getAggrStatsForInternal(req);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=getAggrStatsForInternal, dbName={}, tblName={}, partNames={}",
            req.getDbName(), req.getTblName(), req.getPartNames());
      }
      return v;
    }
    return super.getAggrStatsForInternal(req);
  }

};