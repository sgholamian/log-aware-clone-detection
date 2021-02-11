//,temp,TemplateServiceImpl.java,1168,1191,temp,VolumeServiceImpl.java,1555,1577
//,3
public class xxx {
    protected Void migrateVolumeCallBack(AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> callback, MigrateVolumeContext<VolumeApiResult> context) {
        VolumeInfo srcVolume = context.srcVolume;
        CopyCommandResult result = callback.getResult();
        AsyncCallFuture<VolumeApiResult> future = context.future;
        VolumeApiResult res = new VolumeApiResult(srcVolume);
        try {
            if (result.isFailed()) {
                res.setResult(result.getResult());
                srcVolume.processEvent(Event.OperationFailed);
                future.complete(res);
            } else {
                srcVolume.processEvent(Event.OperationSuccessed);
                snapshotMgr.cleanupSnapshotsByVolume(srcVolume.getId());
                future.complete(res);
            }
        } catch (Exception e) {
            s_logger.error("Failed to process migrate volume callback", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return null;
    }

};