//,temp,VolumeServiceImpl.java,1821,1852,temp,VolumeServiceImpl.java,1632,1660
//,3
public class xxx {
    protected Void migrateVmWithVolumesCallBack(AsyncCallbackDispatcher<VolumeServiceImpl, CopyCommandResult> callback, MigrateVmWithVolumesContext<CommandResult> context) {
        Map<VolumeInfo, DataStore> volumeToPool = context.volumeToPool;
        CopyCommandResult result = callback.getResult();
        AsyncCallFuture<CommandResult> future = context.future;
        CommandResult res = new CommandResult();
        try {
            if (result.isFailed()) {
                res.setResult(result.getResult());
                for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
                    VolumeInfo volume = entry.getKey();
                    volume.processEvent(Event.OperationFailed);
                }
                future.complete(res);
            } else {
                for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
                    VolumeInfo volume = entry.getKey();
                    snapshotMgr.cleanupSnapshotsByVolume(volume.getId());
                    volume.processEvent(Event.OperationSuccessed);
                }
                future.complete(res);
            }
        } catch (Exception e) {
            s_logger.error("Failed to process copy volume callback", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return null;
    }

};