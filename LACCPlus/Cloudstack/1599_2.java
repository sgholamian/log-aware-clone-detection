//,temp,VmwareResource.java,4901,4922,temp,VmwareResource.java,4818,4838
//,3
public class xxx {
    protected Answer execute(BackupSnapshotCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource BackupSnapshotCommand: " + _gson.toJson(cmd));
        }

        try {
            VmwareContext context = getServiceContext();
            VmwareManager mgr = context.getStockObject(VmwareManager.CONTEXT_STOCK_NAME);

            return mgr.getStorageManager().execute(this, cmd);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            String details = "BackupSnapshotCommand failed due to " + VmwareHelper.getExceptionMessage(e);
            s_logger.error(details, e);
            return new BackupSnapshotAnswer(cmd, false, details, null, true);
        }
    }

};