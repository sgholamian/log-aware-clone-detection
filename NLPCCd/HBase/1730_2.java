//,temp,sample_2266.java,2,17,temp,sample_2265.java,2,14
//,3
public class xxx {
private boolean doInit(Configuration conf) {
boolean tuningEnabled = true;
globalMemStorePercent = MemorySizeUtil.getGlobalMemStoreHeapPercent(conf, false);
blockCachePercent = conf.getFloat(HFILE_BLOCK_CACHE_SIZE_KEY, HConstants.HFILE_BLOCK_CACHE_SIZE_DEFAULT);
MemorySizeUtil.checkForClusterFreeHeapMemoryLimit(conf);
globalMemStorePercentMinRange = conf.getFloat(MEMSTORE_SIZE_MIN_RANGE_KEY, globalMemStorePercent);
globalMemStorePercentMaxRange = conf.getFloat(MEMSTORE_SIZE_MAX_RANGE_KEY, globalMemStorePercent);
if (globalMemStorePercent < globalMemStorePercentMinRange) {


log.info("setting to same value as because supplied value greater than initial memstore size value");
}
}

};