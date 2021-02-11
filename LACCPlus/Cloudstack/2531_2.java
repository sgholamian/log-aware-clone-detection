//,temp,SnapshotServiceImpl.java,355,377,temp,SnapshotServiceImpl.java,132,166
//,3
public class xxx {
    protected Void createSnapshotAsyncCallback(AsyncCallbackDispatcher<SnapshotServiceImpl, CreateCmdResult> callback, CreateSnapshotContext<CreateCmdResult> context) {
        CreateCmdResult result = callback.getResult();
        SnapshotObject snapshot = (SnapshotObject)context.snapshot;
        AsyncCallFuture<SnapshotResult> future = context.future;
        SnapshotResult snapResult = new SnapshotResult(snapshot, result.getAnswer());
        if (result.isFailed()) {
            s_logger.debug("create snapshot " + context.snapshot.getName() + " failed: " + result.getResult());
            try {
                snapshot.processEvent(Snapshot.Event.OperationFailed);
                snapshot.processEvent(Event.OperationFailed);
            } catch (Exception e) {
                s_logger.debug("Failed to update snapshot state due to " + e.getMessage());
            }

            snapResult.setResult(result.getResult());
            future.complete(snapResult);
            return null;
        }

        try {
            snapshot.processEvent(Event.OperationSuccessed, result.getAnswer());
            snapshot.processEvent(Snapshot.Event.OperationSucceeded);
        } catch (Exception e) {
            s_logger.debug("Failed to create snapshot: ", e);
            snapResult.setResult(e.toString());
            try {
                snapshot.processEvent(Snapshot.Event.OperationFailed);
            } catch (NoTransitionException e1) {
                s_logger.debug("Failed to change snapshot state: " + e1.toString());
            }
        }

        future.complete(snapResult);
        return null;
    }

};