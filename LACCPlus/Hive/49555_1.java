//,temp,HiveMetaStoreClientWithLocalCache.java,458,494,temp,HiveMetaStoreClientWithLocalCache.java,392,428
//,3
public class xxx {
  @Override
  protected GetPartitionsByNamesResult getPartitionsByNamesInternal(GetPartitionsByNamesRequest rqst) throws TException {
    if (isCacheEnabledAndInitialized()) {
      String dbName = parseDbName(rqst.getDb_name(), conf)[1];
      TableWatermark watermark = new TableWatermark(
          rqst.getValidWriteIdList(), getTable(dbName, rqst.getTbl_name()).getId());
      if (watermark.isValid()) {
        CacheWrapper cache = new CacheWrapper(mscLocalCache);
        // 1) Retrieve from the cache those ids present, gather the rest
        Pair<List<Partition>, List<String>> p = getPartitionsByNamesCache(
            cache, rqst, watermark);
        List<String> partitionsMissing = p.getRight();
        List<Partition> partitions = p.getLeft();
        // 2) If they were all present in the cache, return
        if (partitionsMissing.isEmpty()) {
          return new GetPartitionsByNamesResult(partitions);
        }
        // 3) If they were not, gather the remaining
        GetPartitionsByNamesRequest newRqst = new GetPartitionsByNamesRequest(rqst);
        newRqst.setNames(partitionsMissing);
        GetPartitionsByNamesResult r = super.getPartitionsByNamesInternal(newRqst);
        // 4) Populate the cache
        List<Partition> newPartitions = loadPartitionsByNamesCache(
            cache, r, rqst, watermark);
        // 5) Sort result (in case there is any assumption) and return
        GetPartitionsByNamesResult result = computePartitionsByNamesFinal(rqst, partitions, newPartitions);

        if (LOG.isDebugEnabled() && recordStats) {
          LOG.debug(cacheObjName + ": " + mscLocalCache.stats().toString());
        }

        return result;
      }
    }

    return super.getPartitionsByNamesInternal(rqst);
  }

};