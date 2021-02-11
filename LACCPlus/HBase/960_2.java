//,temp,HRegion.java,5356,5385,temp,HRegion.java,5334,5349
//,3
public class xxx {
  public MemStoreSize dropMemStoreContents() throws IOException {
    MemStoreSizing totalFreedSize = new NonThreadSafeMemStoreSizing();
    this.updatesLock.writeLock().lock();
    try {
      for (HStore s : stores.values()) {
        MemStoreSize memStoreSize = doDropStoreMemStoreContentsForSeqId(s, HConstants.NO_SEQNUM);
        LOG.info("Drop memstore for Store " + s.getColumnFamilyName() + " in region "
                + this.getRegionInfo().getRegionNameAsString()
                + " , dropped memstoresize: [" + memStoreSize + " }");
        totalFreedSize.incMemStoreSize(memStoreSize);
      }
      return totalFreedSize.getMemStoreSize();
    } finally {
      this.updatesLock.writeLock().unlock();
    }
  }

};