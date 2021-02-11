//,temp,VolumeServiceImpl.java,1821,1852,temp,SnapshotServiceImpl.java,545,568
//,3
public class xxx {
    protected Void syncSnapshotCallBack(AsyncCallbackDispatcher<SnapshotServiceImpl, CopyCommandResult> callback,
            CopySnapshotContext<CommandResult> context) {
        CopyCommandResult result = callback.getResult();
        SnapshotInfo destSnapshot = context.destSnapshot;
        SnapshotResult res = new SnapshotResult(destSnapshot, null);

        AsyncCallFuture<SnapshotResult> future = context.future;
        try {
            if (result.isFailed()) {
                res.setResult(result.getResult());
                // no change to existing snapshot_store_ref, will try to re-sync later if other call triggers this sync operation
            } else {
                // this will update install path properly, next time it will not sync anymore.
                destSnapshot.processEvent(Event.OperationSuccessed, result.getAnswer());
            }
            future.complete(res);
        } catch (Exception e) {
            s_logger.debug("Failed to process sync snapshot callback", e);
            res.setResult(e.toString());
            future.complete(res);
        }

        return null;
    }

};