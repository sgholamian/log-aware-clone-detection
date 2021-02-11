//,temp,VolumeServiceImpl.java,1361,1386,temp,VolumeServiceImpl.java,1218,1239
//,3
public class xxx {
    @Override
    public AsyncCallFuture<VolumeApiResult> createVolumeFromSnapshot(VolumeInfo volume, DataStore store, SnapshotInfo snapshot) {
        AsyncCallFuture<VolumeApiResult> future = new AsyncCallFuture<VolumeApiResult>();

        try {
            DataObject volumeOnStore = store.create(volume);
            volumeOnStore.processEvent(Event.CreateOnlyRequested);
            _volumeDetailsDao.addDetail(volume.getId(), SNAPSHOT_ID, Long.toString(snapshot.getId()), false);

            CreateVolumeFromBaseImageContext<VolumeApiResult> context = new CreateVolumeFromBaseImageContext<VolumeApiResult>(null, volume, store, volumeOnStore, future, snapshot);
            AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
            caller.setCallback(caller.getTarget().createVolumeFromSnapshotCallback(null, null)).setContext(context);
            motionSrv.copyAsync(snapshot, volumeOnStore, caller);
        } catch (Exception e) {
            s_logger.debug("create volume from snapshot failed", e);
            VolumeApiResult result = new VolumeApiResult(volume);
            result.setResult(e.toString());
            future.complete(result);
        }

        return future;
    }

};