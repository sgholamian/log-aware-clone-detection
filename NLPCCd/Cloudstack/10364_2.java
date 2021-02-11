//,temp,sample_7062.java,2,15,temp,sample_7061.java,2,14
//,3
public class xxx {
protected Void copyCallback(AsyncCallbackDispatcher<DataObjectManagerImpl, CopyCommandResult> callback, CopyContext<CreateCmdResult> context) {
CopyCommandResult result = callback.getResult();
DataObject destObj = context.destObj;
if (result.isFailed()) {
try {
objectInDataStoreMgr.update(destObj, Event.OperationFailed);
} catch (NoTransitionException e) {


log.info("failed to update copying state");
}
}
}

};