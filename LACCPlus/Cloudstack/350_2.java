//,temp,VolumeServiceImpl.java,1361,1386,temp,VolumeServiceImpl.java,1307,1333
//,3
public class xxx {
    protected AsyncCallFuture<VolumeApiResult> copyVolumeFromImageToPrimary(VolumeInfo srcVolume, DataStore destStore) {
        AsyncCallFuture<VolumeApiResult> future = new AsyncCallFuture<VolumeApiResult>();
        VolumeApiResult res = new VolumeApiResult(srcVolume);
        VolumeInfo destVolume = null;
        try {
            destVolume = (VolumeInfo)destStore.create(srcVolume);
            destVolume.processEvent(Event.CopyingRequested);
            srcVolume.processEvent(Event.CopyingRequested);

            CopyVolumeContext<VolumeApiResult> context = new CopyVolumeContext<VolumeApiResult>(null, future, srcVolume, destVolume, destStore);
            AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
            caller.setCallback(caller.getTarget().copyVolumeFromImageToPrimaryCallback(null, null)).setContext(context);

            motionSrv.copyAsync(srcVolume, destVolume, caller);
            return future;
        } catch (Exception e) {
            s_logger.error("failed to copy volume from image store", e);
            if (destVolume != null) {
                destVolume.processEvent(Event.OperationFailed);
            }

            srcVolume.processEvent(Event.OperationFailed);
            res.setResult(e.toString());
            future.complete(res);
            return future;
        }
    }

};