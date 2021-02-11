//,temp,HRegion.java,5356,5385,temp,HRegion.java,5334,5349
//,3
public class xxx {
  private MemStoreSize dropMemStoreContentsForSeqId(long seqId, HStore store) throws IOException {
    MemStoreSizing totalFreedSize = new NonThreadSafeMemStoreSizing();
    this.updatesLock.writeLock().lock();
    try {

      long currentSeqId = mvcc.getReadPoint();
      if (seqId >= currentSeqId) {
        // then we can drop the memstore contents since everything is below this seqId
        LOG.info(getRegionInfo().getEncodedName() + " : "
            + "Dropping memstore contents as well since replayed flush seqId: "
            + seqId + " is greater than current seqId:" + currentSeqId);

        // Prepare flush (take a snapshot) and then abort (drop the snapshot)
        if (store == null) {
          for (HStore s : stores.values()) {
            totalFreedSize.incMemStoreSize(doDropStoreMemStoreContentsForSeqId(s, currentSeqId));
          }
        } else {
          totalFreedSize.incMemStoreSize(doDropStoreMemStoreContentsForSeqId(store, currentSeqId));
        }
      } else {
        LOG.info(getRegionInfo().getEncodedName() + " : "
            + "Not dropping memstore contents since replayed flush seqId: "
            + seqId + " is smaller than current seqId:" + currentSeqId);
      }
    } finally {
      this.updatesLock.writeLock().unlock();
    }
    return totalFreedSize.getMemStoreSize();
  }

};