//,temp,VolumeServiceImpl.java,1821,1852,temp,VolumeServiceImpl.java,1241,1270
//,3
public class xxx {
    protected Void createVolumeFromSnapshotCallback(AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> callback, CreateVolumeFromBaseImageContext<VolumeApiResult> context) {
        CopyCommandResult result = callback.getResult();
        VolumeInfo volume = (VolumeInfo)context.templateOnStore;
        SnapshotInfo snapshot = context.snapshot;
        VolumeApiResult apiResult = new VolumeApiResult(volume);
        Event event = null;
        if (result.isFailed()) {
            apiResult.setResult(result.getResult());
            event = Event.OperationFailed;
        } else {
            event = Event.OperationSuccessed;
        }

        try {
            if (result.isSuccess()) {
                volume.processEvent(event, result.getAnswer());
            } else {
                volume.processEvent(event);
            }
            _volumeDetailsDao.removeDetail(volume.getId(), SNAPSHOT_ID);

        } catch (Exception e) {
            s_logger.debug("create volume from snapshot failed", e);
            apiResult.setResult(e.toString());
        }

        AsyncCallFuture<VolumeApiResult> future = context.future;
        future.complete(apiResult);
        return null;
    }

};