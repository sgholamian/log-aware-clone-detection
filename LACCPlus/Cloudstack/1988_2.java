//,temp,DataObjectManagerImpl.java,218,249,temp,DataObjectManagerImpl.java,169,205
//,3
public class xxx {
    protected Void createAsynCallback(AsyncCallbackDispatcher<DataObjectManagerImpl, CreateCmdResult> callback, CreateContext<CreateCmdResult> context) {
        CreateCmdResult result = callback.getResult();
        DataObject objInStrore = context.objInStrore;
        CreateCmdResult upResult = new CreateCmdResult(null, null);
        if (result.isFailed()) {
            upResult.setResult(result.getResult());
            context.getParentCallback().complete(upResult);
            return null;
        }

        try {
            objectInDataStoreMgr.update(objInStrore, ObjectInDataStoreStateMachine.Event.OperationSuccessed);
        } catch (NoTransitionException e) {
            try {
                objectInDataStoreMgr.update(objInStrore, ObjectInDataStoreStateMachine.Event.OperationFailed);
            } catch (Exception e1) {
                s_logger.debug("failed to change state", e1);
            }

            upResult.setResult(e.toString());
            context.getParentCallback().complete(upResult);
            return null;
        } catch (ConcurrentOperationException e) {
            try {
                objectInDataStoreMgr.update(objInStrore, ObjectInDataStoreStateMachine.Event.OperationFailed);
            } catch (Exception e1) {
                s_logger.debug("failed to change state", e1);
            }

            upResult.setResult(e.toString());
            context.getParentCallback().complete(upResult);
            return null;
        }

        context.getParentCallback().complete(result);
        return null;
    }

};