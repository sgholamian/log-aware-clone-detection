//,temp,FlowRunCoprocessor.java,244,260,temp,FlowRunCoprocessor.java,223,242
//,3
public class xxx {
  @Override
  public void postFlush(ObserverContext<RegionCoprocessorEnvironment> c,
      Store store, StoreFile resultFile) {
    if (LOG.isDebugEnabled()) {
      if (store != null) {
        LOG.debug("postFlush store = " + store.getColumnFamilyName()
            + " flushableSize=" + store.getFlushableSize()
            + " flushedCellsCount=" + store.getFlushedCellsCount()
            + " compactedCellsCount=" + store.getCompactedCellsCount()
            + " majorCompactedCellsCount="
            + store.getMajorCompactedCellsCount() + " memstoreFlushSize="
            + store.getMemstoreFlushSize() + " memstoreSize="
            + store.getMemStoreSize() + " size=" + store.getSize()
            + " storeFilesCount=" + store.getStorefilesCount());
      }
    }
  }

};