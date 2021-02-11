//,temp,DataObjectManagerImpl.java,218,249,temp,DataObjectManagerImpl.java,169,205
//,3
public class xxx {
    @Override
    public void copyAsync(DataObject srcData, DataObject destData, AsyncCompletionCallback<CreateCmdResult> callback) {
        try {
            objectInDataStoreMgr.update(destData, ObjectInDataStoreStateMachine.Event.CopyingRequested);
        } catch (NoTransitionException e) {
            s_logger.debug("failed to change state", e);
            try {
                objectInDataStoreMgr.update(destData, ObjectInDataStoreStateMachine.Event.OperationFailed);
            } catch (Exception e1) {
                s_logger.debug("failed to further change state to OperationFailed", e1);
            }
            CreateCmdResult res = new CreateCmdResult(null, null);
            res.setResult("Failed to change state: " + e.toString());
            callback.complete(res);
        } catch (ConcurrentOperationException e) {
            s_logger.debug("failed to change state", e);
            try {
                objectInDataStoreMgr.update(destData, ObjectInDataStoreStateMachine.Event.OperationFailed);
            } catch (Exception e1) {
                s_logger.debug("failed to further change state to OperationFailed", e1);
            }
            CreateCmdResult res = new CreateCmdResult(null, null);
            res.setResult("Failed to change state: " + e.toString());
            callback.complete(res);
        }

        CopyContext<CreateCmdResult> anotherCall = new CopyContext<CreateCmdResult>(callback, srcData, destData);
        AsyncCallbackDispatcher<DataObjectManagerImpl, CopyCommandResult> caller = AsyncCallbackDispatcher.create(this);
        caller.setCallback(caller.getTarget().copyCallback(null, null)).setContext(anotherCall);

        motionSrv.copyAsync(srcData, destData, caller);
    }

};