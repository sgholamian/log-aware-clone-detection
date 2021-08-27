//,temp,sample_1070.java,2,20,temp,sample_1066.java,2,20
//,3
public class xxx {
public void dummy_method(){
for (int colIx = 0; colIx < splitIncludes.length; ++colIx) {
if (!splitIncludes[colIx]) continue;
List<CacheWriter.CacheStreamData> streams = diskData.colStreams.get(colIx);
LlapSerDeDataBuffer[][] newCacheDataForCol = createArrayToCache(sliceToCache, colIx, streams);
if (streams == null) continue;
Iterator<CacheWriter.CacheStreamData> iter = streams.iterator();
while (iter.hasNext()) {
CacheWriter.CacheStreamData stream = iter.next();
if (stream.isSuppressed) {
if (LlapIoImpl.LOG.isTraceEnabled()) {


log.info("removing a suppressed stream");
}
}
}
}
}

};