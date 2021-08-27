//,temp,HiveMetaStoreClientWithLocalCache.java,458,494,temp,HiveMetaStoreClientWithLocalCache.java,392,428
//,3
public class xxx {
  @Override
  protected TableStatsResult getTableColumnStatisticsInternal(TableStatsRequest req) throws TException {
    if (isCacheEnabledAndInitialized()) {
      TableWatermark watermark = new TableWatermark(
          getValidWriteIdList(req.getDbName(), req.getTblName()),
          getTable(req.getDbName(), req.getTblName()).getId());
      if (watermark.isValid()) {
        CacheWrapper cache = new CacheWrapper(mscLocalCache);
        // 1) Retrieve from the cache those ids present, gather the rest
        Pair<List<ColumnStatisticsObj>, List<String>> p = getTableColumnStatisticsCache(
            cache, req, watermark);
        List<String> colStatsMissing = p.getRight();
        List<ColumnStatisticsObj> colStats = p.getLeft();
        // 2) If they were all present in the cache, return
        if (colStatsMissing.isEmpty()) {
          return new TableStatsResult(colStats);
        }
        // 3) If they were not, gather the remaining
        TableStatsRequest newRqst = new TableStatsRequest(req);
        newRqst.setColNames(colStatsMissing);
        TableStatsResult r = super.getTableColumnStatisticsInternal(newRqst);
        // 4) Populate the cache
        List<ColumnStatisticsObj> newColStats = loadTableColumnStatisticsCache(
            cache, r, req, watermark);
        // 5) Sort result (in case there is any assumption) and return
        TableStatsResult result = computeTableColumnStatisticsFinal(req, colStats, newColStats);

        if (LOG.isDebugEnabled() && recordStats) {
          LOG.debug(cacheObjName + ": " + mscLocalCache.stats().toString());
        }

        return result;
      }
    }

    return super.getTableColumnStatisticsInternal(req);
  }

};