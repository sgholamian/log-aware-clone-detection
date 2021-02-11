//,temp,SnapshotServiceImpl.java,428,457,temp,SnapshotServiceImpl.java,400,426
//,3
public class xxx {
    @Override
    public boolean revertSnapshot(SnapshotInfo snapshot) {
        SnapshotInfo snapshotOnPrimaryStore = _snapshotFactory.getSnapshot(snapshot.getId(), DataStoreRole.Primary);
        if (snapshotOnPrimaryStore == null) {
            throw new CloudRuntimeException("Cannot find an entry for snapshot " + snapshot.getId() + " on primary storage pools");
        }
        PrimaryDataStore store = (PrimaryDataStore)snapshotOnPrimaryStore.getDataStore();

        AsyncCallFuture<SnapshotResult> future = new AsyncCallFuture<SnapshotResult>();
        RevertSnapshotContext<CommandResult> context = new RevertSnapshotContext<CommandResult>(null, snapshot, future);
        AsyncCallbackDispatcher<SnapshotServiceImpl, CommandResult> caller = AsyncCallbackDispatcher.create(this);
        caller.setCallback(caller.getTarget().revertSnapshotCallback(null, null)).setContext(context);

        ((PrimaryDataStoreDriver)store.getDriver()).revertSnapshot(snapshot, snapshotOnPrimaryStore, caller);

        SnapshotResult result = null;
        try {
            result = future.get();
            if (result.isFailed()) {
                throw new CloudRuntimeException(result.getResult());
            }
            return true;
        } catch (InterruptedException e) {
            s_logger.debug("revert snapshot is failed: " + e.toString());
        } catch (ExecutionException e) {
            s_logger.debug("revert snapshot is failed: " + e.toString());
        }

        return false;
    }

};