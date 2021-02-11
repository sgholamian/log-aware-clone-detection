//,temp,DataObjectManagerImpl.java,251,294,temp,SnapshotServiceImpl.java,132,166
//,3
public class xxx {
    protected Void copyCallback(AsyncCallbackDispatcher<DataObjectManagerImpl, CopyCommandResult> callback, CopyContext<CreateCmdResult> context) {
        CopyCommandResult result = callback.getResult();
        DataObject destObj = context.destObj;

        if (result.isFailed()) {
            try {
                objectInDataStoreMgr.update(destObj, Event.OperationFailed);
            } catch (NoTransitionException e) {
                s_logger.debug("Failed to update copying state", e);
            } catch (ConcurrentOperationException e) {
                s_logger.debug("Failed to update copying state", e);
            }
            CreateCmdResult res = new CreateCmdResult(null, null);
            res.setResult(result.getResult());
            context.getParentCallback().complete(res);
        }

        try {
            objectInDataStoreMgr.update(destObj, ObjectInDataStoreStateMachine.Event.OperationSuccessed);
        } catch (NoTransitionException e) {
            s_logger.debug("Failed to update copying state: ", e);
            try {
                objectInDataStoreMgr.update(destObj, ObjectInDataStoreStateMachine.Event.OperationFailed);
            } catch (Exception e1) {
                s_logger.debug("failed to further change state to OperationFailed", e1);
            }
            CreateCmdResult res = new CreateCmdResult(null, null);
            res.setResult("Failed to update copying state: " + e.toString());
            context.getParentCallback().complete(res);
        } catch (ConcurrentOperationException e) {
            s_logger.debug("Failed to update copying state: ", e);
            try {
                objectInDataStoreMgr.update(destObj, ObjectInDataStoreStateMachine.Event.OperationFailed);
            } catch (Exception e1) {
                s_logger.debug("failed to further change state to OperationFailed", e1);
            }
            CreateCmdResult res = new CreateCmdResult(null, null);
            res.setResult("Failed to update copying state: " + e.toString());
            context.getParentCallback().complete(res);
        }
        CreateCmdResult res = new CreateCmdResult(result.getPath(), null);
        context.getParentCallback().complete(res);
        return null;
    }

};