//,temp,SnapshotServiceImpl.java,428,457,temp,SnapshotServiceImpl.java,400,426
//,3
public class xxx {
    @Override
    public boolean deleteSnapshot(SnapshotInfo snapInfo) {
        snapInfo.processEvent(ObjectInDataStoreStateMachine.Event.DestroyRequested);

        AsyncCallFuture<SnapshotResult> future = new AsyncCallFuture<SnapshotResult>();
        DeleteSnapshotContext<CommandResult> context = new DeleteSnapshotContext<CommandResult>(null, snapInfo, future);
        AsyncCallbackDispatcher<SnapshotServiceImpl, CommandResult> caller = AsyncCallbackDispatcher.create(this);
        caller.setCallback(caller.getTarget().deleteSnapshotCallback(null, null)).setContext(context);
        DataStore store = snapInfo.getDataStore();
        store.getDriver().deleteAsync(store, snapInfo, caller);

        SnapshotResult result = null;
        try {
            result = future.get();
            if (result.isFailed()) {
                throw new CloudRuntimeException(result.getResult());
            }
            return true;
        } catch (InterruptedException e) {
            s_logger.debug("delete snapshot is failed: " + e.toString());
        } catch (ExecutionException e) {
            s_logger.debug("delete snapshot is failed: " + e.toString());
        }

        return false;

    }

};