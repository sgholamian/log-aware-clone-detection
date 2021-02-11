//,temp,TemplateServiceImpl.java,1168,1191,temp,SnapshotServiceImpl.java,355,377
//,3
public class xxx {
    protected Void deleteSnapshotCallback(AsyncCallbackDispatcher<SnapshotServiceImpl, CommandResult> callback, DeleteSnapshotContext<CommandResult> context) {

        CommandResult result = callback.getResult();
        AsyncCallFuture<SnapshotResult> future = context.future;
        SnapshotInfo snapshot = context.snapshot;
        SnapshotResult res = null;
        try {
            if (result.isFailed()) {
                s_logger.debug("delete snapshot failed" + result.getResult());
                snapshot.processEvent(ObjectInDataStoreStateMachine.Event.OperationFailed);
                res = new SnapshotResult(context.snapshot, null);
                res.setResult(result.getResult());
            } else {
                snapshot.processEvent(ObjectInDataStoreStateMachine.Event.OperationSuccessed);
                res = new SnapshotResult(context.snapshot, null);
            }
        } catch (Exception e) {
            s_logger.debug("Failed to in deleteSnapshotCallback", e);
            res.setResult(e.toString());
        }
        future.complete(res);
        return null;
    }

};