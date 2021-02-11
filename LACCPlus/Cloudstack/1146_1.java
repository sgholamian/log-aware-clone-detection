//,temp,VolumeServiceImpl.java,1361,1386,temp,VolumeServiceImpl.java,1218,1239
//,3
public class xxx {
    protected AsyncCallFuture<VolumeApiResult> copyVolumeFromPrimaryToImage(VolumeInfo srcVolume, DataStore destStore) {
        AsyncCallFuture<VolumeApiResult> future = new AsyncCallFuture<VolumeApiResult>();
        VolumeApiResult res = new VolumeApiResult(srcVolume);
        VolumeInfo destVolume = null;
        try {
            destVolume = (VolumeInfo)destStore.create(srcVolume);
            srcVolume.processEvent(Event.MigrationRequested);    // this is just used for locking that src volume record in DB to avoid using lock
            destVolume.processEventOnly(Event.CreateOnlyRequested);

            CopyVolumeContext<VolumeApiResult> context = new CopyVolumeContext<VolumeApiResult>(null, future, srcVolume, destVolume, destStore);
            AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
            caller.setCallback(caller.getTarget().copyVolumeFromPrimaryToImageCallback(null, null)).setContext(context);

            motionSrv.copyAsync(srcVolume, destVolume, caller);
            return future;
        } catch (Exception e) {
            s_logger.error("failed to copy volume to image store", e);
            if (destVolume != null) {
                destVolume.getDataStore().delete(destVolume);
            }
            srcVolume.processEvent(Event.OperationFailed); // unlock source volume record
            res.setResult(e.toString());
            future.complete(res);
            return future;
        }
    }

};