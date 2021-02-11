//,temp,sample_7070.java,2,15,temp,sample_7069.java,2,14
//,3
public class xxx {
protected Void deleteAsynCallback(AsyncCallbackDispatcher<DataObjectManagerImpl, CommandResult> callback, DeleteContext<CommandResult> context) {
DataObject destObj = context.obj;
CommandResult res = callback.getResult();
if (res.isFailed()) {
try {
objectInDataStoreMgr.update(destObj, Event.OperationFailed);
} catch (NoTransitionException e) {


log.info("delete failed");
}
}
}

};