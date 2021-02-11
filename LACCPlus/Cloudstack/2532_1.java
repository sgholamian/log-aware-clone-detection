//,temp,SnapshotServiceImpl.java,379,398,temp,SnapshotServiceImpl.java,355,377
//,3
public class xxx {
    protected Void revertSnapshotCallback(AsyncCallbackDispatcher<SnapshotServiceImpl, CommandResult> callback, RevertSnapshotContext<CommandResult> context) {

        CommandResult result = callback.getResult();
        AsyncCallFuture<SnapshotResult> future = context.future;
        SnapshotResult res = null;
        try {
            if (result.isFailed()) {
                s_logger.debug("revert snapshot failed" + result.getResult());
                res = new SnapshotResult(context.snapshot, null);
                res.setResult(result.getResult());
            } else {
                res = new SnapshotResult(context.snapshot, null);
            }
        } catch (Exception e) {
            s_logger.debug("Failed to in revertSnapshotCallback", e);
            res.setResult(e.toString());
        }
        future.complete(res);
        return null;
    }

};