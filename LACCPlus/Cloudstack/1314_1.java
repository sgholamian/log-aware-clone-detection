//,temp,SnapshotServiceImpl.java,315,353,temp,SnapshotServiceImpl.java,132,166
//,3
public class xxx {
    protected Void copySnapshotAsyncCallback(AsyncCallbackDispatcher<SnapshotServiceImpl, CopyCommandResult> callback, CopySnapshotContext<CommandResult> context) {
        CopyCommandResult result = callback.getResult();
        SnapshotInfo destSnapshot = context.destSnapshot;
        SnapshotObject srcSnapshot = (SnapshotObject)context.srcSnapshot;
        Object payload = srcSnapshot.getPayload();
        CreateSnapshotPayload createSnapshotPayload = (CreateSnapshotPayload)payload;
        AsyncCallFuture<SnapshotResult> future = context.future;
        SnapshotResult snapResult = new SnapshotResult(destSnapshot, result.getAnswer());
        if (result.isFailed()) {
            try {
                if (createSnapshotPayload.getAsyncBackup()) {
                    destSnapshot.processEvent(Event.OperationFailed);
                    throw new SnapshotBackupException("Failed in creating backup of snapshot with ID "+srcSnapshot.getId());
                } else {
                    destSnapshot.processEvent(Event.OperationFailed);
                    //if backup snapshot failed, mark srcSnapshot in snapshot_store_ref as failed also
                    cleanupOnSnapshotBackupFailure(context.srcSnapshot);
                }
            } catch (SnapshotBackupException e) {
                s_logger.debug("Failed to create backup: " + e.toString());
            }
            snapResult.setResult(result.getResult());
            future.complete(snapResult);
            return null;
        }

        try {
            CopyCmdAnswer copyCmdAnswer = (CopyCmdAnswer)result.getAnswer();
            destSnapshot.processEvent(Event.OperationSuccessed, copyCmdAnswer);
            srcSnapshot.processEvent(Snapshot.Event.OperationSucceeded);
            snapResult = new SnapshotResult(_snapshotFactory.getSnapshot(destSnapshot.getId(), destSnapshot.getDataStore()), copyCmdAnswer);
            future.complete(snapResult);
        } catch (Exception e) {
            s_logger.debug("Failed to update snapshot state", e);
            snapResult.setResult(e.toString());
            future.complete(snapResult);
        }
        return null;
    }

};