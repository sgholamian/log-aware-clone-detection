//,temp,sample_4187.java,2,11,temp,sample_4193.java,2,9
//,3
public class xxx {
public FileData getFileData(Object fileKey, long start, long end, boolean[] includes, DiskRangeListFactory factory, LowLevelCacheCounters qfCounters, BooleanRef gotAllData) throws IOException {
FileCache<FileData> subCache = cache.get(fileKey);
if (subCache == null || !subCache.incRef()) {
if (LlapIoImpl.CACHE_LOGGER.isTraceEnabled()) {


log.info("cannot find cache for in");
}
}
}

};