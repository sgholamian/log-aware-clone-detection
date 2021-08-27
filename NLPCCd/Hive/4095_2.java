//,temp,sample_3455.java,2,16,temp,sample_892.java,2,15
//,3
public class xxx {
private DiskRangeList.MutateHelper getDataFromCacheAndDisk(DiskRangeList listToRead, long stripeOffset, boolean hasFileId, IdentityHashMap<ByteBuffer, Boolean> toRelease) throws IOException {
DiskRangeList.MutateHelper toRead = new DiskRangeList.MutateHelper(listToRead);
if (LOG.isInfoEnabled()) {
}
BooleanRef isAllInCache = new BooleanRef();
if (hasFileId) {
cacheWrapper.getFileData(fileKey, toRead.next, stripeOffset, CC_FACTORY, isAllInCache);
if (LOG.isInfoEnabled()) {


log.info("disk ranges after cache found everything file base offset");
}
}
}

};