//,temp,sample_1305.java,2,16,temp,sample_1307.java,2,15
//,3
public class xxx {
protected Answer execute(CreatePrivateTemplateFromSnapshotCommand cmd) {
if (s_logger.isInfoEnabled()) {
}
try {
VmwareManager mgr = getServiceContext().getStockObject(VmwareManager.CONTEXT_STOCK_NAME);
return mgr.getStorageManager().execute(this, cmd);
} catch (Throwable e) {
if (e instanceof RemoteException) {


log.info("encounter remote exception to vcenter invalidate vmware session context");
}
}
}

};