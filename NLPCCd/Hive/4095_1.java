//,temp,sample_3455.java,2,16,temp,sample_892.java,2,15
//,3
public class xxx {
public int read(byte[] array, final int arrayOffset, final int len) throws IOException {
long readStartPos = position;
DiskRangeList drl = new DiskRangeList(readStartPos, readStartPos + len);
DataCache.BooleanRef gotAllData = new DataCache.BooleanRef();
drl = cache.getFileData(fileKey, drl, 0, new DataCache.DiskRangeListFactory() {
public DiskRangeList createCacheChunk( MemoryBuffer buffer, long startOffset, long endOffset) {
return new CacheChunk(buffer, startOffset, endOffset);
}
}, gotAllData);
if (LOG.isInfoEnabled()) {


log.info("buffers after cache");
}
}

};