//,temp,TemplateServiceImpl.java,992,1023,temp,SnapshotServiceImpl.java,497,526
//,3
public class xxx {
    private void syncSnapshotToRegionStore(long snapshotId, DataStore store){
        // if snapshot is already on region wide object store, check if it is really downloaded there (by checking install_path). Sync snapshot to region
        // wide store if it is not there physically.
        SnapshotInfo snapOnStore = _snapshotFactory.getSnapshot(snapshotId, store);
        if (snapOnStore == null) {
            throw new CloudRuntimeException("Cannot find an entry in snapshot_store_ref for snapshot " + snapshotId + " on region store: " + store.getName());
        }
        if (snapOnStore.getPath() == null || snapOnStore.getPath().length() == 0) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("sync snapshot " + snapshotId + " from cache to object store...");
            }
            // snapshot is not on region store yet, sync to region store
            SnapshotInfo srcSnapshot = _snapshotFactory.getReadySnapshotOnCache(snapshotId);
            if (srcSnapshot == null) {
                throw new CloudRuntimeException("Cannot find snapshot " + snapshotId + "  on cache store");
            }
            AsyncCallFuture<SnapshotResult> future = syncToRegionStoreAsync(srcSnapshot, store);
            try {
                SnapshotResult result = future.get();
                if (result.isFailed()) {
                    throw new CloudRuntimeException("sync snapshot from cache to region wide store failed for image store " + store.getName() + ":"
                            + result.getResult());
                }
                _cacheMgr.releaseCacheObject(srcSnapshot); // reduce reference count for template on cache, so it can recycled by schedule
            } catch (Exception ex) {
                throw new CloudRuntimeException("sync snapshot from cache to region wide store failed for image store " + store.getName());
            }
        }

    }

};