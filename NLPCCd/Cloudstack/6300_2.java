//,temp,sample_1333.java,2,16,temp,sample_1301.java,2,16
//,3
public class xxx {
protected Answer execute(BackupSnapshotCommand cmd) {
if (s_logger.isInfoEnabled()) {
}
try {
VmwareContext context = getServiceContext();
VmwareManager mgr = context.getStockObject(VmwareManager.CONTEXT_STOCK_NAME);
return mgr.getStorageManager().execute(this, cmd);
} catch (Throwable e) {
if (e instanceof RemoteException) {


log.info("encounter remote exception to vcenter invalidate vmware session context");
}
}
}

};