//,temp,sample_3457.java,2,9,temp,sample_3456.java,2,10
//,3
public class xxx {
private void copyDiskDataToCacheBuffer(byte[] diskData, int offsetInDiskData, int sizeToCopy, ByteBuffer cacheBuffer, DiskRange[] cacheRanges, int cacheRangeIx, long cacheRangeStart) {
int bbPos = cacheBuffer.position();
long cacheRangeEnd = cacheRangeStart + sizeToCopy;
if (LOG.isTraceEnabled()) {


log.info("caching");
}
}

};