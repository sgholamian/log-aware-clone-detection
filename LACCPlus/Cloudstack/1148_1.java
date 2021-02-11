//,temp,VolumeServiceImpl.java,1529,1553,temp,VolumeServiceImpl.java,1218,1239
//,3
public class xxx {
    @Override
    public AsyncCallFuture<VolumeApiResult> migrateVolume(VolumeInfo srcVolume, DataStore destStore) {
        AsyncCallFuture<VolumeApiResult> future = new AsyncCallFuture<VolumeApiResult>();
        VolumeApiResult res = new VolumeApiResult(srcVolume);
        try {
            if (!snapshotMgr.canOperateOnVolume(srcVolume)) {
                s_logger.debug("Snapshots are being created on this volume. This volume cannot be migrated now.");
                res.setResult("Snapshots are being created on this volume. This volume cannot be migrated now.");
                future.complete(res);
                return future;
            }

            VolumeInfo destVolume = volFactory.getVolume(srcVolume.getId(), destStore);
            srcVolume.processEvent(Event.MigrationRequested);
            MigrateVolumeContext<VolumeApiResult> context = new MigrateVolumeContext<VolumeApiResult>(null, future, srcVolume, destVolume, destStore);
            AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
            caller.setCallback(caller.getTarget().migrateVolumeCallBack(null, null)).setContext(context);
            motionSrv.copyAsync(srcVolume, destVolume, caller);
        } catch (Exception e) {
            s_logger.debug("Failed to copy volume", e);
            res.setResult(e.toString());
            future.complete(res);
        }
        return future;
    }

};